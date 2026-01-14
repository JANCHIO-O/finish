package com.example.library.common.controller;

import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.UserAccountRepository;
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
@RequestMapping("/subsystem")
public class SubsystemLoginController {

    private final UserAccountRepository userAccountRepository;

    public SubsystemLoginController(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String target, Model model) {
        model.addAttribute("target", target);
        return "subsystem/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String accountId,
                              @RequestParam String password,
                              @RequestParam(required = false) String target,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Optional<UserAccount> account = userAccountRepository.findByAccountIdAndRole(accountId, "TEACHER");
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            session.setAttribute("teacherAccountId", accountId);
            redirectAttributes.addFlashAttribute("message", "登录成功，欢迎进入子系统。");
            if (target != null && !target.isBlank()) {
                return "redirect:" + target;
            }
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("message", "账号或密码错误，仅教师账号可登录。");
        if (target != null && !target.isBlank()) {
            return "redirect:/subsystem/login?target=" + URLEncoder.encode(target, StandardCharsets.UTF_8);
        }
        return "redirect:/subsystem/login";
    }
}
