package com.example.library.acquisition;

import com.example.library.acquisition.entity.AcquisitionOrder;
import com.example.library.acquisition.service.AcquisitionService;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/acquisition")
public class AcquisitionController {

    private final AcquisitionService acquisitionService;

    public AcquisitionController(AcquisitionService acquisitionService) {
        this.acquisitionService = acquisitionService;
    }

    @GetMapping
    public String index() {
        return "acquisition/index";
    }

    @GetMapping("/collect")
    public String collect(Model model) {
        model.addAttribute("acquisitionList", acquisitionService.listAcquisitions());
        return "acquisition/collect";
    }

    @PostMapping("/collect/add")
    public String addCollect(@RequestParam String title,
                             @RequestParam String author,
                             @RequestParam String isbn,
                             @RequestParam String publisher,
                             @RequestParam String docType) {
        acquisitionService.addAcquisitionRecord(title, author, isbn, publisher, docType);
        return "redirect:/acquisition/collect";
    }

    @GetMapping("/order")
    public String order(Model model,
                        @RequestParam(required = false) String title,
                        @RequestParam(required = false) String author,
                        @RequestParam(required = false) String isbn,
                        @RequestParam(required = false) String publisher,
                        @RequestParam(required = false) String docType) {
        model.addAttribute("orderList", acquisitionService.listOrders());
        model.addAttribute("prefillTitle", title);
        model.addAttribute("prefillAuthor", author);
        model.addAttribute("prefillIsbn", isbn);
        model.addAttribute("prefillPublisher", publisher);
        model.addAttribute("prefillDocType", docType);
        return "acquisition/order";
    }

    @PostMapping("/order/add")
    public String addOrder(@RequestParam String title,
                           @RequestParam String author,
                           @RequestParam String isbn,
                           @RequestParam String publisher,
                           @RequestParam String docType,
                           @RequestParam String orderer,
                           @RequestParam String orderDate,
                           @RequestParam Integer quantity,
                           @RequestParam Double unitPrice) {
        acquisitionService.addOrder(title, author, isbn, publisher, docType, orderer, orderDate, quantity, unitPrice);
        return "redirect:/acquisition/order";
    }

    @GetMapping("/verify")
    public String verify(Model model,
                         @RequestParam(required = false) String orderId) {
        model.addAttribute("acceptanceList", acquisitionService.listAcceptanceRecords());
        model.addAttribute("today", LocalDate.now());
        if (orderId != null && !orderId.isBlank()) {
            AcquisitionOrder order = acquisitionService.getOrder(orderId);
            model.addAttribute("prefillOrder", order);
        }
        return "acquisition/verify";
    }

    @PostMapping("/verify/add")
    public String addAcceptance(@RequestParam String orderId,
                                @RequestParam String checker,
                                @RequestParam String acceptanceDate,
                                @RequestParam Integer receivedQuantity,
                                @RequestParam String status,
                                RedirectAttributes redirectAttributes) {
        try {
            acquisitionService.addAcceptanceRecord(orderId, checker, acceptanceDate, receivedQuantity, status);
            return "redirect:/acquisition/verify";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/acquisition/verify?orderId=" + orderId;
        }
    }

    @GetMapping("/modify")
    public String modify(Model model,
                         @RequestParam(required = false) String orderId) {
        model.addAttribute("orderList", acquisitionService.listOrders());
        if (orderId != null && !orderId.isBlank()) {
            model.addAttribute("editingOrder", acquisitionService.getOrder(orderId));
        }
        return "acquisition/modify";
    }

    @PostMapping("/modify/update")
    public String updateOrder(@RequestParam String orderId,
                              @RequestParam String orderer,
                              @RequestParam String orderDate,
                              @RequestParam Integer quantity,
                              @RequestParam Double unitPrice) {
        acquisitionService.updateOrder(orderId, orderer, orderDate, quantity, unitPrice);
        return "redirect:/acquisition/modify";
    }

    @GetMapping("/return")
    public String returnPage(Model model) {
        model.addAttribute("returnList", acquisitionService.listReturnRecords());
        return "acquisition/return";
    }

    @GetMapping("/return/apply")
    public String returnApply(Model model,
                              @RequestParam String orderId) {
        AcquisitionOrder order = acquisitionService.getOrder(orderId);
        model.addAttribute("prefillOrder", order);
        return "acquisition/return-apply";
    }

    @PostMapping("/return/add")
    public String addReturn(@RequestParam String orderDate,
                            @RequestParam String orderer,
                            @RequestParam String title,
                            @RequestParam String author,
                            @RequestParam String isbn,
                            @RequestParam String publisher,
                            @RequestParam String docType,
                            @RequestParam Double unitPrice,
                            @RequestParam String currency,
                            @RequestParam Integer orderQuantity,
                            @RequestParam String orderStatus,
                            @RequestParam Integer returnQuantity,
                            @RequestParam String returner,
                            @RequestParam String returnReason) {
        acquisitionService.addReturnRecord(orderDate, orderer, title, author, isbn, publisher, docType, unitPrice, currency,
                orderQuantity, orderStatus, returnQuantity, returner, returnReason);
        return "redirect:/acquisition/return";
    }
}
