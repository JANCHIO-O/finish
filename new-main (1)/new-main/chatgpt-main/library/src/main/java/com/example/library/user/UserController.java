package com.example.library.user;

import com.example.library.common.entity.ReaderInfo;
import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.ReaderInfoRepository;
import com.example.library.common.repository.UserAccountRepository;
import jakarta.servlet.http.HttpSession;
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

    public UserController(UserAccountRepository userAccountRepository, ReaderInfoRepository readerInfoRepository) {
        this.userAccountRepository = userAccountRepository;
        this.readerInfoRepository = readerInfoRepository;
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
            redirectAttributes.addFlashAttribute("message", "登录成功，请维护个人信息。");
            return "redirect:/user/info";
        }
        redirectAttributes.addFlashAttribute("message", "账号或密码错误，请重试。");
        return "redirect:/user/login";
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
        redirectAttributes.addFlashAttribute("message", "个人信息已更新。");
        return "redirect:/user/info";
    }

    @GetMapping("/manage")
    public String manage(Model model) {
        List<ReaderInfo> readerInfos = readerInfoRepository.findAll();
        model.addAttribute("readerInfos", readerInfos);
        return "user/manage";
    }

    @PostMapping("/manage/delete")
    public String deleteAccount(@RequestParam String role,
                                @RequestParam String accountId,
                                RedirectAttributes redirectAttributes) {
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
        redirectAttributes.addFlashAttribute("message", "账号已删除，并同步清理用户注册表与用户信息表。");
        return "redirect:/user/manage";
    }
}
