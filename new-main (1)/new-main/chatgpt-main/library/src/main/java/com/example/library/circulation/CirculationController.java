//package com.example.library.circulation;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/circulation")
//public class CirculationController {
//
//    @GetMapping
//    public String index() { return "circulation/index"; }
//
//    @GetMapping("/borrow")
//    public String borrow() { return "circulation/borrow"; }
//
//    @GetMapping("/return")
//    public String returnPage() { return "circulation/return"; }
//
//    @GetMapping("/reserve")
//    public String reserve() { return "circulation/reserve"; }
//
//    @GetMapping("/overdue")
//    public String overdue() { return "circulation/overdue"; }
//
//    @GetMapping("/info")
//    public String info() { return "circulation/info"; }
//}

package com.example.library.circulation;

import com.example.library.circulation.dto.BorrowRecordDto;
import com.example.library.circulation.dto.CirculationBookDto;
import com.example.library.circulation.dto.NoticeDto;
import com.example.library.circulation.service.CirculationService;
import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.UserAccountRepository;
import com.example.library.system.service.SystemLogService;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/circulation")
public class CirculationController {
    private final CirculationService circulationService;
    private final UserAccountRepository userAccountRepository;
    private final SystemLogService systemLogService;

    public CirculationController(CirculationService circulationService,
                                 UserAccountRepository userAccountRepository,
                                 SystemLogService systemLogService) {
        this.circulationService = circulationService;
        this.userAccountRepository = userAccountRepository;
        this.systemLogService = systemLogService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String target, Model model) {
        model.addAttribute("target", target);
        return "circulation/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String accountId,
                              @RequestParam String password,
                              @RequestParam(required = false) String target,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Optional<UserAccount> account = userAccountRepository.findByAccountIdAndRole(accountId, "STUDENT");
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            session.setAttribute("circulationAccountId", accountId);
            systemLogService.log("用户", "登录", "学生账号 " + accountId + " 登录流通管理子系统。");
            redirectAttributes.addFlashAttribute("message", "登录成功，欢迎进入流通管理子系统。");
            if (target != null && !target.isBlank()) {
                return "redirect:" + target;
            }
            return "redirect:/circulation";
        }
        redirectAttributes.addFlashAttribute("message", "账号或密码错误，仅学生账号可登录。");
        if (target != null && !target.isBlank()) {
            return "redirect:/circulation/login?target="
                    + URLEncoder.encode(target, StandardCharsets.UTF_8);
        }
        return "redirect:/circulation/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("circulationAccountId");
        return "redirect:/circulation/login";
    }

    // 流通子系统首页 - 匹配前端：/circulation
    @GetMapping("")
    public String index(Model model) {
        // 查询系统公告展示在首页
        List<NoticeDto> notices = circulationService.getAllNotices();
        model.addAttribute("notices", notices);
        return "circulation/index";
    }

    // 借阅页面跳转 - 匹配前端：/circulation/borrow
    @GetMapping("/borrow")
    public String borrow() {
        return "circulation/borrow";
    }

    // 借阅接口 - 匹配前端ajax请求：/circulation/api/borrow
    @PostMapping("/api/borrow")
    @ResponseBody
    public String borrowBook(@RequestParam String cardNo, @RequestParam String bookId) {
        return circulationService.borrowBook(cardNo, bookId);
    }

    // 还书页面跳转 - 匹配前端：/circulation/return
    @GetMapping("/return")
    public String returnPage() {
        return "circulation/return";
    }

    // 还书接口 - 前端ajax通用post请求
    @PostMapping("/api/return")
    @ResponseBody
    public String returnBook(@RequestParam String cardNo, @RequestParam String bookId) {
        return circulationService.returnBook(cardNo, bookId);
    }

    // 预约页面跳转 - 匹配前端：/circulation/reserve
    @GetMapping("/reserve")
    public String reserve() {
        return "circulation/reserve";
    }

    // 预约接口 - 匹配前端ajax请求：/circulation/api/reserve
    @PostMapping("/api/reserve")
    @ResponseBody
    public String reserveBook(@RequestParam String cardNo, @RequestParam String bookId) {
        return circulationService.reserveBook(cardNo, bookId);
    }

    // 超期页面跳转+数据查询 - 匹配前端：/circulation/overdue
    @GetMapping("/overdue")
    public String overdue(Model model) {
        List<BorrowRecordDto> overdueList = circulationService.getOverdueRecords();
        model.addAttribute("overdueList", overdueList);
        return "circulation/overdue";
    }

    // 图书流通信息查询 - 匹配前端：/circulation/info
    @GetMapping("/info")
    public String info(Model model) {
        List<CirculationBookDto> bookList = circulationService.getAvailableBooks();
        model.addAttribute("bookList", bookList);
        return "circulation/info";
    }

    // 个人公告与通知 - 匹配前端：/circulation/notice-manage
    @GetMapping("/notice-manage")
    public String myNotices(HttpSession session, Model model) {
        String cardNo = (String) session.getAttribute("cardNo");
        List<NoticeDto> noticeList = circulationService.getValidNotices(cardNo);
        model.addAttribute("noticeList", noticeList);
        return "circulation/notice";
    }

    // 兼容原有根路径访问
    @GetMapping("/bookList")
    public String getBookList(Model model) {
        List<CirculationBookDto> list = circulationService.getAvailableBooks();
        model.addAttribute("bookList", list);
        return "circulation/info";
    }
}
