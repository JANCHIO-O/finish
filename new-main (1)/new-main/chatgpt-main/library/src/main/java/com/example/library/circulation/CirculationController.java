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
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/circulation")
public class CirculationController {
    @Autowired
    private CirculationService circulationService;

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