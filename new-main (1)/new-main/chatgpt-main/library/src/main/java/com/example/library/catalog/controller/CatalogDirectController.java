package com.example.library.catalog.controller;

import com.example.library.catalog.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CatalogDirectController {

    private final CatalogService catalogService;

    public CatalogDirectController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog/direct")
    public String direct(@RequestParam(required = false) String isbn,
                         @RequestParam(required = false) String bookName,
                         Model model) {
        model.addAttribute("isbn", isbn == null ? "" : isbn);
        model.addAttribute("bookName", bookName == null ? "" : bookName);
        return "catalog/catalog-direct";
    }

    @PostMapping("/catalog/direct/submit")
    public String submit(@RequestParam String isbn,
                         @RequestParam String bookName,
                         @RequestParam String cataloger,
                         Model model) {

        String result = catalogService.directCatalogAndRemovePending(isbn, bookName, cataloger);

        if ("DUPLICATE".equals(result)) {
            model.addAttribute("error", "该ISBN已存在于图书流通库，已从验收清单移除，无需重复编目。");
            model.addAttribute("isbn", isbn);
            model.addAttribute("bookName", bookName);
            return "catalog/catalog-direct";
        }

        return "redirect:/catalog/list";
    }

    @GetMapping("/catalog/list")
    public String list(Model model) {
        model.addAttribute("catalogList", catalogService.listCatalogBatch());
        return "catalog/catalog-list";
    }
}
