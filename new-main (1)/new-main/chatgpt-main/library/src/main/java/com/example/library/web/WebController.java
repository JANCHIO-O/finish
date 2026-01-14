package com.example.library.web;

import com.example.library.common.entity.CirculationBook;
import com.example.library.common.entity.UserAccount;
import com.example.library.web.service.WebService;
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
@RequestMapping("/web")
public class WebController {

    private final WebService webService;

    public WebController(WebService webService) {
        this.webService = webService;
    }

    @GetMapping("/login")
    public String login() {
        return "web/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String accountId,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Optional<UserAccount> account = webService.authenticate(accountId, password);
        if (account.isPresent()) {
            session.setAttribute("webUserId", account.get().getAccountId());
            session.setAttribute("webUserRole", account.get().getRole());
            redirectAttributes.addFlashAttribute("message", "登录成功，欢迎进入 Web 检索子系统。");
            return "redirect:/web";
        }
        redirectAttributes.addFlashAttribute("message", "账号或密码错误，请重试。");
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
            return "redirect:/web/login";
        }
        model.addAttribute("accountId", session.getAttribute("webUserId"));
        model.addAttribute("role", session.getAttribute("webUserRole"));
        return "web/index";
    }

    @GetMapping("/notice")
    public String notice(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/web/login";
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
            return "redirect:/web/login";
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
            return "redirect:/web/login";
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
            return "redirect:/web/login";
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
            return "redirect:/web/login";
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
            return "redirect:/web/login";
        }
        webService.addMessage(readerName, content);
        redirectAttributes.addFlashAttribute("messageStatus", "留言已提交，感谢您的反馈。");
        return "redirect:/web/message";
    }

    @GetMapping("/overdue")
    public String overdue(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/web/login";
        }
        model.addAttribute("overdueNotices", webService.listOverdueNotices());
        return "web/overdue";
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("webUserId") != null;
    }
}
