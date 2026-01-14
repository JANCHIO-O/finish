package com.example.library.catalog.controller;

import com.example.library.catalog.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class CatalogDamageController {

    private final CatalogService catalogService;

    public CatalogDamageController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog/damage/request")
    public String damageRequestPage(Model model) {
        model.addAttribute("circulationList", catalogService.listCirculationBooks());
        model.addAttribute("today", LocalDate.now());
        return "catalog/catalog-damage-request";
    }

    @PostMapping("/catalog/damage/request/submit")
    public String submitDamageRequest(@RequestParam String isbn,
                                      @RequestParam String damageReason,
                                      @RequestParam String applicant) {
        catalogService.submitDamageRequest(
                isbn,
                damageReason,
                applicant,
                LocalDate.now()
        );
        return "redirect:/catalog/damage/request";
    }

    @GetMapping("/catalog/damage/approve")
    public String approvePage(Model model) {
        model.addAttribute("damageRequests", catalogService.listDamageRequests());
        return "catalog/catalog-damage-approve";
    }

    @PostMapping("/catalog/damage/approve/submit")
    public String approve(@RequestParam String requestId,
                          @RequestParam String decision,
                          @RequestParam String operator) {
        if ("approve".equals(decision)) {
            catalogService.approveDamageRequest(requestId, operator);
        } else {
            catalogService.rejectDamageRequest(requestId);
        }
        return "redirect:/catalog/damage/approve";
    }

    @GetMapping("/catalog/damage/records")
    public String recordsPage(Model model) {
        model.addAttribute("damageRecords", catalogService.listDamageRecords());
        return "catalog/catalog-damage-records";
    }
}
