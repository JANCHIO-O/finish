package com.example.library.user;

import com.example.library.common.entity.ReaderInfo;
import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.ReaderInfoRepository;
import com.example.library.common.repository.UserAccountRepository;
import com.example.library.system.service.SystemLogService;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserAccountRepository userAccountRepository;
    private final ReaderInfoRepository readerInfoRepository;
    private final SystemLogService systemLogService;

    public UserController(UserAccountRepository userAccountRepository,
                          ReaderInfoRepository readerInfoRepository,
                          SystemLogService systemLogService) {
        this.userAccountRepository = userAccountRepository;
        this.readerInfoRepository = readerInfoRepository;
        this.systemLogService = systemLogService;
    }

    @GetMapping
    public String index() { return "user/index"; }

    @GetMapping("/register")
    public String register() { return "user/register"; }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String role,
                                 @RequestParam String accountId,
                                 @RequestParam String password,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("message", "两次输入的密码不一致，注册失败。");
            return "redirect:/user/register";
        }
        if (userAccountRepository.existsByAccountIdAndRole(accountId, role)) {
            redirectAttributes.addFlashAttribute("message", "该账号已存在，请直接登录。");
            return "redirect:/user/register";
        }
        userAccountRepository.save(new UserAccount(accountId, role, password));
        redirectAttributes.addFlashAttribute("message", "注册成功，请登录并维护个人信息。");
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String login() { return "user/login"; }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String role,
                              @RequestParam String accountId,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Optional<UserAccount> account = userAccountRepository.findByAccountIdAndRole(accountId, role);
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            session.setAttribute("currentUserId", accountId);
            session.setAttribute("currentUserRole", role);
            systemLogService.log("用户", "登录", "用户账号 " + accountId + " 登录个人信息维护模块。");
            redirectAttributes.addFlashAttribute("message", "登录成功，请维护个人信息。");
            return "redirect:/user/info";
        }
        redirectAttributes.addFlashAttribute("message", "账号或密码错误，请重试。");
        return "redirect:/user/login";
    }

    @GetMapping("/manage/login")
    public String manageLogin(@RequestParam(required = false) String target, Model model) {
        model.addAttribute("target", target);
        return "user/manage-login";
    }

    @PostMapping("/manage/login")
    public String manageLoginSubmit(@RequestParam String accountId,
                                    @RequestParam String password,
                                    @RequestParam(required = false) String target,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        Optional<UserAccount> account = userAccountRepository.findByAccountIdAndRole(accountId, "ADMIN");
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            session.setAttribute("userManageAdminId", accountId);
            systemLogService.log("管理员", "登录", "管理员账号 " + accountId + " 登录用户管理页面。");
            redirectAttributes.addFlashAttribute("message", "登录成功，欢迎进入用户管理页面。");
            if (target != null && !target.isBlank()) {
                return "redirect:" + target;
            }
            return "redirect:/user/manage";
        }
        redirectAttributes.addFlashAttribute("message", "账号或密码错误，仅管理员账号可登录。");
        if (target != null && !target.isBlank()) {
            return "redirect:/user/manage/login?target="
                    + URLEncoder.encode(target, StandardCharsets.UTF_8);
        }
        return "redirect:/user/manage/login";
    }

    @GetMapping("/manage/logout")
    public String manageLogout(HttpSession session) {
        session.removeAttribute("userManageAdminId");
        return "redirect:/user/manage/login";
    }

    @GetMapping("/info")
    public String info(HttpSession session, Model model) {
        String accountId = (String) session.getAttribute("currentUserId");
        if (accountId != null) {
            readerInfoRepository.findById(accountId).ifPresent(info -> model.addAttribute("readerInfo", info));
            model.addAttribute("accountId", accountId);
            model.addAttribute("role", session.getAttribute("currentUserRole"));
        }
        return "user/info";
    }

    @PostMapping("/info")
    public String updateInfo(@RequestParam String name,
                             @RequestParam String gender,
                             @RequestParam String mobile,
                             @RequestParam String password,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        String accountId = (String) session.getAttribute("currentUserId");
        String role = (String) session.getAttribute("currentUserRole");
        if (accountId == null || role == null) {
            redirectAttributes.addFlashAttribute("message", "请先登录后再维护信息。");
            return "redirect:/user/login";
        }
        if (!name.matches("^[\\u4e00-\\u9fff]+$")) {
            redirectAttributes.addFlashAttribute("message", "姓名必须为汉字。");
            return "redirect:/user/info";
        }
        if (!("男".equals(gender) || "女".equals(gender))) {
            redirectAttributes.addFlashAttribute("message", "性别只能选择男或女。");
            return "redirect:/user/info";
        }
        if (!mobile.matches("^\\d{11}$")) {
            redirectAttributes.addFlashAttribute("message", "手机号必须为11位数字。");
            return "redirect:/user/info";
        }
        Optional<UserAccount> account = userAccountRepository.findByAccountIdAndRole(accountId, role);
        if (account.isEmpty() || !account.get().getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("message", "密码验证失败，无法修改信息。");
            return "redirect:/user/info";
        }
        ReaderInfo info = readerInfoRepository.findById(accountId)
                .orElseGet(() -> new ReaderInfo(accountId, role, name, gender, mobile, "", ""));
        info.setRole(role);
        info.setName(name);
        info.setGender(gender);
        info.setMobile(mobile);
        readerInfoRepository.save(info);
        systemLogService.log("用户", "修改", "用户账号 " + accountId + " 更新个人信息。");
        redirectAttributes.addFlashAttribute("message", "个人信息已更新。");
        return "redirect:/user/info";
    }

    @GetMapping("/manage")
    public String manage(HttpSession session, Model model) {
        if (!isUserManageLoggedIn(session)) {
            return "redirect:/user/manage/login?target=/user/manage";
        }
        List<ReaderInfo> readerInfos = readerInfoRepository.findAll();
        model.addAttribute("readerInfos", readerInfos);
        return "user/manage";
    }

    @PostMapping("/manage/delete")
    public String deleteAccount(@RequestParam String role,
                                @RequestParam String accountId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (!isUserManageLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("message", "请先使用管理员账号登录用户管理页面。");
            return "redirect:/user/manage/login?target=/user/manage";
        }
        boolean accountExists = userAccountRepository.existsByAccountId(accountId);
        boolean infoExists = readerInfoRepository.existsById(accountId);
        if (!accountExists && !infoExists) {
            redirectAttributes.addFlashAttribute("message", "指定账号不存在。");
            return "redirect:/user/manage";
        }
        if (accountExists) {
            userAccountRepository.deleteByAccountId(accountId);
        }
        if (infoExists) {
            readerInfoRepository.deleteById(accountId);
        }
        systemLogService.log("管理员", "删除", "管理员删除账号 " + accountId + "，并清理用户信息记录。");
        redirectAttributes.addFlashAttribute("message", "账号已删除，并同步清理用户注册表与用户信息表。");
        return "redirect:/user/manage";
    }

    private boolean isUserManageLoggedIn(HttpSession session) {
        return session.getAttribute("userManageAdminId") != null;
    }
}
