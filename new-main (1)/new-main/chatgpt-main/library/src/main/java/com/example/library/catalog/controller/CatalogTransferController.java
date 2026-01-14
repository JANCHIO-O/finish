package com.example.library.catalog.controller;

import com.example.library.catalog.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CatalogTransferController {

    private final CatalogService catalogService;

    public CatalogTransferController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog/transfer")
    public String transfer(Model model) {
        model.addAttribute("catalogList", catalogService.listCatalogBatch());
        return "catalog/catalog-transfer";
    }

    @PostMapping("/catalog/transfer/submit")
    public String submit(@RequestParam String targetLocation) {
        catalogService.transferBatch(targetLocation);
        return "redirect:/catalog/transfer/list";
    }

    @GetMapping("/catalog/transfer/list")
    public String transferList(Model model) {
        model.addAttribute("transferList", catalogService.listLatestTransfer());
        return "catalog/catalog-transfer-list";
    }
}
