package com.example.library.system;

import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.UserAccountRepository;
import com.example.library.system.entity.SystemInfo;
import com.example.library.system.repository.SystemLogRepository;
import com.example.library.system.repository.SystemInfoRepository;
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
    private final SystemInfoRepository systemInfoRepository;
    private final SystemLogRepository systemLogRepository;
    private final SystemLogService systemLogService;

    public SystemController(UserAccountRepository userAccountRepository,
                            SystemInfoRepository systemInfoRepository,
                            SystemLogRepository systemLogRepository,
                            SystemLogService systemLogService) {
        this.userAccountRepository = userAccountRepository;
        this.systemInfoRepository = systemInfoRepository;
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
    public String users(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/system/login";
        }
        List<UserAccount> accounts = userAccountRepository.findByRoleNot("ADMIN");
        model.addAttribute("accounts", accounts);
        return "system/users";
    }

    @PostMapping("/users/password")
    public String updatePassword(HttpSession session,
                                 @RequestParam String accountId,
                                 @RequestParam String newPassword,
                                 RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) {
            return "redirect:/system/login";
        }
        if (newPassword == null || newPassword.isBlank()) {
            redirectAttributes.addFlashAttribute("message", "新密码不能为空。");
            return "redirect:/system/users";
        }
        Optional<UserAccount> account = userAccountRepository.findByAccountId(accountId);
        if (account.isEmpty() || "ADMIN".equals(account.get().getRole())) {
            redirectAttributes.addFlashAttribute("message", "无法修改管理员账号或不存在的账号。");
            return "redirect:/system/users";
        }
        account.get().setPassword(newPassword);
        userAccountRepository.save(account.get());
        String adminId = (String) session.getAttribute("systemAdminId");
        systemLogService.log("管理员", "修改", "管理员账号 " + adminId + " 修改账号 " + accountId + " 的密码。");
        redirectAttributes.addFlashAttribute("message", "密码已更新。");
        return "redirect:/system/users";
    }

    @GetMapping("/announcement")
    public String announcement(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/system/login";
        }
        SystemInfo systemInfo = systemInfoRepository.findFirstByOrderByIdAsc()
                .orElseGet(SystemInfo::new);
        model.addAttribute("systemInfo", systemInfo);
        return "system/announcement";
    }

    @PostMapping("/announcement")
    public String updateSystemInfo(HttpSession session,
                                   @RequestParam String systemName,
                                   @RequestParam String overview,
                                   @RequestParam String coreFunctions,
                                   @RequestParam String serviceScope,
                                   @RequestParam String securityGuarantee,
                                   @RequestParam String maintenanceSupport,
                                   RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) {
            return "redirect:/system/login";
        }
        SystemInfo systemInfo = systemInfoRepository.findFirstByOrderByIdAsc()
                .orElseGet(SystemInfo::new);
        systemInfo.setSystemName(systemName);
        systemInfo.setOverview(overview);
        systemInfo.setCoreFunctions(coreFunctions);
        systemInfo.setServiceScope(serviceScope);
        systemInfo.setSecurityGuarantee(securityGuarantee);
        systemInfo.setMaintenanceSupport(maintenanceSupport);
        systemInfoRepository.save(systemInfo);
        String adminId = (String) session.getAttribute("systemAdminId");
        systemLogService.log("管理员", "修改", "管理员账号 " + adminId + " 更新系统详细信息。");
        redirectAttributes.addFlashAttribute("message", "系统详细信息已更新。");
        return "redirect:/system/announcement";
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

    
}
