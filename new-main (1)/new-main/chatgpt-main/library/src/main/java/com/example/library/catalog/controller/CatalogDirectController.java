package com.example.library.catalog.controller;

import com.example.library.catalog.service.CatalogService;
import com.example.library.common.entity.AcceptanceRecord;
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
        String resolvedBookName = bookName;
        String author = "";
        String publisher = "";
        String docType = "";
        if (isbn != null && !isbn.isBlank()) {
            AcceptanceRecord acceptance = catalogService.findAcceptanceByIsbn(isbn);
            if (acceptance != null) {
                if (resolvedBookName == null || resolvedBookName.isBlank()) {
                    resolvedBookName = acceptance.getTitle();
                }
                author = acceptance.getAuthor();
                publisher = acceptance.getPublisher();
                docType = acceptance.getDocType();
            }
        }
        model.addAttribute("isbn", isbn == null ? "" : isbn);
        model.addAttribute("bookName", resolvedBookName == null ? "" : resolvedBookName);
        model.addAttribute("author", author == null ? "" : author);
        model.addAttribute("publisher", publisher == null ? "" : publisher);
        model.addAttribute("docType", docType == null ? "" : docType);
        return "catalog/catalog-direct";
    }

    @PostMapping("/catalog/direct/submit")
    public String submit(@RequestParam String isbn,
                         @RequestParam String bookName,
                         @RequestParam String author,
                         @RequestParam String publisher,
                         @RequestParam String docType,
                         @RequestParam String cataloger,
                         Model model) {

        String result = catalogService.directCatalogAndRemovePending(
                isbn, bookName, author, publisher, docType, cataloger
        );

        if ("DUPLICATE".equals(result)) {
            model.addAttribute("error", "该ISBN已存在于图书流通库，已从验收清单移除，无需重复编目。");
            model.addAttribute("isbn", isbn);
            model.addAttribute("bookName", bookName);
            model.addAttribute("author", author);
            model.addAttribute("publisher", publisher);
            model.addAttribute("docType", docType);
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
