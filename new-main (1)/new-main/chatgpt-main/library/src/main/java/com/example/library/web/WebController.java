package com.example.library.web;

import com.example.library.common.entity.CirculationBook;
import com.example.library.common.entity.UserAccount;
import com.example.library.system.service.SystemLogService;
import com.example.library.web.service.WebService;
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
@RequestMapping("/web")
public class WebController {

    private final WebService webService;
    private final SystemLogService systemLogService;

    public WebController(WebService webService, SystemLogService systemLogService) {
        this.webService = webService;
        this.systemLogService = systemLogService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String target, Model model) {
        model.addAttribute("target", target);
        return "web/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String accountId,
                              @RequestParam String password,
                              @RequestParam(required = false) String target,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Optional<UserAccount> account = webService.authenticate(accountId, password);
        if (account.isPresent() && isAllowedRole(account.get().getRole())) {
            session.setAttribute("webUserId", account.get().getAccountId());
            session.setAttribute("webUserRole", account.get().getRole());
            systemLogService.log("用户", "登录",
                    "账号 " + account.get().getAccountId() + " 登录 Web 检索子系统。");
            redirectAttributes.addFlashAttribute("message", "登录成功，欢迎进入 Web 检索子系统。");
            if (target != null && !target.isBlank() && target.startsWith("/web")) {
                return "redirect:" + target;
            }
            return "redirect:/web";
        }
        redirectAttributes.addFlashAttribute("message", "账号或密码错误，仅学生或教师账号可登录。");
        if (target != null && !target.isBlank()) {
            return "redirect:/web/login?target=" + encodeTarget(target);
        }
        return "redirect:/web/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("webUserId");
        session.removeAttribute("webUserRole");
        return "redirect:/web/login";
    }

    @GetMapping
    public String index(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/web/login?target=/web";
        }
        model.addAttribute("accountId", session.getAttribute("webUserId"));
        model.addAttribute("role", session.getAttribute("webUserRole"));
        return "web/index";
    }

    @GetMapping("/notice")
    public String notice(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/web/login?target=/web/notice";
        }
        model.addAttribute("newBooks", webService.listNewBooks());
        return "web/notice";
    }

    @GetMapping("/query")
    public String query(HttpSession session,
                        Model model,
                        @RequestParam(required = false) String category,
                        @RequestParam(required = false) String title,
                        @RequestParam(required = false) String author,
                        @RequestParam(required = false) String isbn,
                        @RequestParam(required = false) String keyword) {
        if (!isLoggedIn(session)) {
            return "redirect:/web/login?target=/web/query";
        }
        List<CirculationBook> results = webService.searchBooks(category, title, author, isbn, keyword);
        model.addAttribute("categories", webService.listCategories());
        model.addAttribute("results", results);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("isbn", isbn);
        model.addAttribute("keyword", keyword);
        return "web/query";
    }

    @GetMapping("/announce")
    public String announce(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/web/login?target=/web/announce";
        }
        model.addAttribute("announcements", webService.listAnnouncements());
        model.addAttribute("role", session.getAttribute("webUserRole"));
        return "web/announce";
    }

    @PostMapping("/announce")
    public String addAnnouncement(HttpSession session,
                                  @RequestParam String title,
                                  @RequestParam String content,
                                  RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) {
            return "redirect:/web/login?target=/web/announce";
        }
        String role = (String) session.getAttribute("webUserRole");
        if (!"TEACHER".equals(role) && !"ADMIN".equals(role)) {
            redirectAttributes.addFlashAttribute("announceError", "仅管理员可发布公告。");
            return "redirect:/web/announce";
        }
        String publisher = (String) session.getAttribute("webUserId");
        webService.addAnnouncement(title, content, publisher);
        redirectAttributes.addFlashAttribute("announceMessage", "公告已发布。");
        return "redirect:/web/announce";
    }

    @GetMapping("/message")
    public String message(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/web/login?target=/web/message";
        }
        model.addAttribute("messages", webService.listMessages());
        return "web/message";
    }

    @PostMapping("/message")
    public String addMessage(HttpSession session,
                             @RequestParam String readerName,
                             @RequestParam String content,
                             RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) {
            return "redirect:/web/login?target=/web/message";
        }
        webService.addMessage(readerName, content);
        redirectAttributes.addFlashAttribute("messageStatus", "留言已提交，感谢您的反馈。");
        return "redirect:/web/message";
    }

    @GetMapping("/overdue")
    public String overdue(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/web/login?target=/web/overdue";
        }
        model.addAttribute("overdueNotices", webService.listOverdueNotices());
        return "web/overdue";
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("webUserId") != null;
    }

    private boolean isAllowedRole(String role) {
        return "STUDENT".equals(role) || "TEACHER".equals(role);
    }

    private String encodeTarget(String target) {
        return URLEncoder.encode(target, StandardCharsets.UTF_8);
    }
}
