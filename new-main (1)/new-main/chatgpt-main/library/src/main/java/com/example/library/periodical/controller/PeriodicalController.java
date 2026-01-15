package com.example.library.periodical.controller;

import com.example.library.periodical.service.PeriodicalService;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/periodical")
public class PeriodicalController {

    private final PeriodicalService periodicalService;

    public PeriodicalController(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }

    @GetMapping
    public String index() { return "periodical/index"; }

    @GetMapping("/visit")
    public String visit(Model model) {
        model.addAttribute("visitList", periodicalService.listVisits());
        model.addAttribute("today", LocalDate.now());
        return "periodical/visit";
    }

    @PostMapping("/visit/add")
    public String addVisit(@RequestParam String title,
                           @RequestParam String issn,
                           @RequestParam String publisher,
                           @RequestParam String recommender,
                           @RequestParam String reason) {
        periodicalService.addVisitRecord(title, issn, publisher, recommender, reason);
        return "redirect:/periodical/visit";
    }

    @GetMapping("/order")
    public String order(Model model,
                        @RequestParam(required = false) String title,
                        @RequestParam(required = false) String issn,
                        @RequestParam(required = false) String publisher) {
        model.addAttribute("orderList", periodicalService.listOrders());
        model.addAttribute("prefillTitle", title);
        model.addAttribute("prefillIssn", issn);
        model.addAttribute("prefillPublisher", publisher);
        model.addAttribute("today", LocalDate.now());
        return "periodical/order";
    }

    @PostMapping("/order/add")
    public String addOrder(@RequestParam String title,
                           @RequestParam String issn,
                           @RequestParam String publisher,
                           @RequestParam Integer quantity,
                           @RequestParam Double unitPrice) {
        periodicalService.addOrder(title, issn, publisher, quantity, unitPrice);
        return "redirect:/periodical/order";
    }

    @GetMapping("/verify")
    public String verify(Model model,
                         @RequestParam(required = false) String orderId,
                         @RequestParam(required = false) String title,
                         @RequestParam(required = false) String issn,
                         @RequestParam(required = false) String publisher,
                         @RequestParam(required = false) Integer quantity) {
        model.addAttribute("acceptanceList", periodicalService.listAcceptanceRecords());
        model.addAttribute("boundIssns", periodicalService.listBoundIssns());
        model.addAttribute("prefillOrderId", orderId);
        model.addAttribute("prefillTitle", title);
        model.addAttribute("prefillIssn", issn);
        model.addAttribute("prefillPublisher", publisher);
        model.addAttribute("prefillQuantity", quantity);
        model.addAttribute("today", LocalDate.now());
        return "periodical/verify";
    }

    @PostMapping("/verify/add")
    public String addAcceptance(@RequestParam String orderId,
                                @RequestParam String title,
                                @RequestParam String issn,
                                @RequestParam String publisher,
                                @RequestParam Integer receivedQuantity,
                                @RequestParam String checker,
                                @RequestParam String status) {
        periodicalService.addAcceptanceRecord(orderId, title, issn, publisher, receivedQuantity, checker, status);
        return "redirect:/periodical/verify";
    }

    @GetMapping("/bind")
    public String bind(Model model,
                       @RequestParam(required = false) String title,
                       @RequestParam(required = false) String issn,
                       @RequestParam(required = false) String publisher) {
        model.addAttribute("bindingList", periodicalService.listBindingRecords());
        model.addAttribute("catalogList", periodicalService.listCatalogEntries());
        model.addAttribute("prefillTitle", title);
        model.addAttribute("prefillIssn", issn);
        model.addAttribute("prefillPublisher", publisher);
        model.addAttribute("today", LocalDate.now());
        return "periodical/bind";
    }

    @PostMapping("/bind/add")
    public String addBind(@RequestParam String title,
                          @RequestParam String issn,
                          @RequestParam String publisher,
                          @RequestParam String binder,
                          @RequestParam String shelfLocation) {
        periodicalService.addBindingRecordAndCatalog(title, issn, publisher, binder, shelfLocation);
        return "redirect:/periodical/bind";
    }

    @GetMapping("/query")
    public String query(Model model) {
        model.addAttribute("catalogList", periodicalService.listCatalogEntries());
        return "periodical/query";
    }
}
