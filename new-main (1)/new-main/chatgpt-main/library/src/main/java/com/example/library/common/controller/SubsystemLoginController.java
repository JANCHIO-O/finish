package com.example.library.common.controller;

import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.UserAccountRepository;
import com.example.library.system.service.SystemLogService;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SubsystemLoginController {

    private final UserAccountRepository userAccountRepository;
    private final SystemLogService systemLogService;

    public SubsystemLoginController(UserAccountRepository userAccountRepository,
                                    SystemLogService systemLogService) {
        this.userAccountRepository = userAccountRepository;
        this.systemLogService = systemLogService;
    }

    @GetMapping("/catalog/login")
    public String catalogLogin(@RequestParam(required = false) String target, Model model) {
        return renderLogin("编目子系统", "/catalog/login", target, model);
    }

    @PostMapping("/catalog/login")
    public String catalogLoginSubmit(@RequestParam String accountId,
                                     @RequestParam String password,
                                     @RequestParam(required = false) String target,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        return handleTeacherLogin(accountId, password, target, session, redirectAttributes,
                "catalogAccountId", "/catalog", "/catalog/login", "编目子系统");
    }

    @GetMapping("/catalog/logout")
    public String catalogLogout(HttpSession session) {
        session.removeAttribute("catalogAccountId");
        return "redirect:/catalog/login";
    }

    @GetMapping("/acquisition/login")
    public String acquisitionLogin(@RequestParam(required = false) String target, Model model) {
        return renderLogin("采访子系统", "/acquisition/login", target, model);
    }

    @PostMapping("/acquisition/login")
    public String acquisitionLoginSubmit(@RequestParam String accountId,
                                         @RequestParam String password,
                                         @RequestParam(required = false) String target,
                                         HttpSession session,
                                         RedirectAttributes redirectAttributes) {
        return handleTeacherLogin(accountId, password, target, session, redirectAttributes,
                "acquisitionAccountId", "/acquisition", "/acquisition/login", "采访子系统");
    }

    @GetMapping("/acquisition/logout")
    public String acquisitionLogout(HttpSession session) {
        session.removeAttribute("acquisitionAccountId");
        return "redirect:/acquisition/login";
    }

    @GetMapping("/periodical/login")
    public String periodicalLogin(@RequestParam(required = false) String target, Model model) {
        return renderLogin("期刊管理子系统", "/periodical/login", target, model);
    }

    @PostMapping("/periodical/login")
    public String periodicalLoginSubmit(@RequestParam String accountId,
                                        @RequestParam String password,
                                        @RequestParam(required = false) String target,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes) {
        return handleTeacherLogin(accountId, password, target, session, redirectAttributes,
                "periodicalAccountId", "/periodical", "/periodical/login", "期刊管理子系统");
    }

    @GetMapping("/periodical/logout")
    public String periodicalLogout(HttpSession session) {
        session.removeAttribute("periodicalAccountId");
        return "redirect:/periodical/login";
    }

    @GetMapping("/statistics/login")
    public String statisticsLogin(@RequestParam(required = false) String target, Model model) {
        return renderLogin("统计打印子系统", "/statistics/login", target, model);
    }

    @PostMapping("/statistics/login")
    public String statisticsLoginSubmit(@RequestParam String accountId,
                                        @RequestParam String password,
                                        @RequestParam(required = false) String target,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes) {
        return handleTeacherLogin(accountId, password, target, session, redirectAttributes,
                "statisticsAccountId", "/statistics", "/statistics/login", "统计打印子系统");
    }

    @GetMapping("/statistics/logout")
    public String statisticsLogout(HttpSession session) {
        session.removeAttribute("statisticsAccountId");
        return "redirect:/statistics/login";
    }

    private String renderLogin(String subsystemName, String actionUrl, String target, Model model) {
        model.addAttribute("subsystemName", subsystemName);
        model.addAttribute("actionUrl", actionUrl);
        model.addAttribute("target", target);
        return "subsystem/login";
    }

    private String handleTeacherLogin(String accountId,
                                      String password,
                                      String target,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes,
                                      String sessionKey,
                                      String fallbackRedirect,
                                      String loginPath,
                                      String subsystemName) {
        Optional<UserAccount> account = userAccountRepository.findByAccountIdAndRole(accountId, "TEACHER");
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            session.setAttribute(sessionKey, accountId);
            systemLogService.log("用户", "登录", "教师账号 " + accountId + " 登录" + subsystemName + "。");
            redirectAttributes.addFlashAttribute("message", "登录成功，欢迎进入" + subsystemName + "。");
            if (target != null && !target.isBlank()) {
                return "redirect:" + target;
            }
            return "redirect:" + fallbackRedirect;
        }
        redirectAttributes.addFlashAttribute("message", "账号或密码错误，仅教师账号可登录。");
        if (target != null && !target.isBlank()) {
            return "redirect:" + loginPath + "?target=" + URLEncoder.encode(target, StandardCharsets.UTF_8);
        }
        return "redirect:" + loginPath;
    }
}
