package com.example.library.system;

import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.UserAccountRepository;
import com.example.library.system.entity.SystemPolicy;
import com.example.library.system.repository.SystemLogRepository;
import com.example.library.system.repository.SystemPolicyRepository;
import com.example.library.system.service.SystemLogService;
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
@RequestMapping("/system")
public class SystemController {

    private final UserAccountRepository userAccountRepository;
    private final SystemPolicyRepository systemPolicyRepository;
    private final SystemLogRepository systemLogRepository;
    private final SystemLogService systemLogService;

    public SystemController(UserAccountRepository userAccountRepository,
                            SystemPolicyRepository systemPolicyRepository,
                            SystemLogRepository systemLogRepository,
                            SystemLogService systemLogService) {
        this.userAccountRepository = userAccountRepository;
        this.systemPolicyRepository = systemPolicyRepository;
        this.systemLogRepository = systemLogRepository;
        this.systemLogService = systemLogService;
    }

    @GetMapping("/login")
    public String login() {
        return "system/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String accountId,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Optional<UserAccount> account = userAccountRepository.findByAccountIdAndRole(accountId, "ADMIN");
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            session.setAttribute("systemAdminId", accountId);
            systemLogService.log("管理员", "登录", "管理员账号 " + accountId + " 登录系统维护子系统。");
            redirectAttributes.addFlashAttribute("message", "登录成功，欢迎进入系统维护子系统。");
            return "redirect:/system";
        }
        redirectAttributes.addFlashAttribute("message", "账号或密码错误，请重试。");
        return "redirect:/system/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("systemAdminId");
        return "redirect:/system/login";
    }

    @GetMapping
    public String index(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/system/login";
        }
        model.addAttribute("adminId", session.getAttribute("systemAdminId"));
        return "system/index";
    }

    @GetMapping("/users")
    public String users(HttpSession session,
                        @RequestParam(required = false) String role,
                        Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/system/login";
        }
        String selectedRole = normalizeRole(role);
        List<UserAccount> accounts = userAccountRepository.findByRole(selectedRole);
        model.addAttribute("accounts", accounts);
        model.addAttribute("selectedRole", selectedRole);
        return "system/users";
    }

    @GetMapping("/announcement")
    public String announcement(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/system/login";
        }
        List<SystemPolicy> policies = systemPolicyRepository.findAll();
        model.addAttribute("policies", policies);
        return "system/announcement";
    }

    @GetMapping("/log")
    public String log(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/system/login";
        }
        model.addAttribute("logs", systemLogRepository.findAllByOrderByActionTimeDesc());
        return "system/log";
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("systemAdminId") != null;
    }

    private String normalizeRole(String role) {
        if ("STUDENT".equals(role)) {
            return "STUDENT";
        }
        return "TEACHER";
    }
}
