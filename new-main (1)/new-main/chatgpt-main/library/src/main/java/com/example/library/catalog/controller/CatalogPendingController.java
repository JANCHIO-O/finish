package com.example.library.catalog.controller;

import com.example.library.catalog.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
@Controller
@RequestMapping("/catalog")
public class CatalogPendingController {

    private final CatalogService catalogService;

    public CatalogPendingController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /** 4.1 待编书目查看：展示验收清单 */
    @GetMapping("/pending")
    public String pending(Model model) {
        model.addAttribute("pendingList", catalogService.listPendingFromAcceptance());
        return "catalog/catalog-pending";
    }

    /** 4.1 手动添加：写入 acceptance_record */
    @PostMapping("/pending/add")
    public String addPending(@RequestParam String title,
                             @RequestParam String isbn,
                             @RequestParam String publisher,
                             @RequestParam String docType,
                             @RequestParam String checker,
                             @RequestParam String publishDate,
                             Model model) {

        String normalizedIsbn = normalizeIsbn(isbn);
        if (normalizedIsbn.length() != 13) {
            model.addAttribute("error", "ISBN 需为 13 位数字（可包含短横线，会自动忽略）。");
            model.addAttribute("pendingList", catalogService.listPendingFromAcceptance());
            return "catalog/catalog-pending";
        }

        // publishDate 页面传 yyyy-MM-dd
        Date date = Date.valueOf(publishDate);

        catalogService.addAcceptanceRecordManually(
                title, normalizedIsbn, publisher, docType, checker, date
        );

        return "redirect:/catalog/pending";
    }

    private String normalizeIsbn(String isbn) {
        if (isbn == null) {
            return "";
        }
        return isbn.replaceAll("\\D", "");
    }
}
