package com.example.library.catalog.controller;

import com.example.library.catalog.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CatalogNoticeController {

    private final CatalogService catalogService;

    public CatalogNoticeController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog/notice")
    public String notice(Model model) {
        model.addAttribute("transferList", catalogService.listLatestTransfer());
        return "catalog/catalog-notice";
    }

    @PostMapping("/catalog/notice/publish")
    public String publishNotice() {
        return "redirect:/catalog/notice";
    }
}