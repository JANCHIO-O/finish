package com.example.library.catalog.controller;

import com.example.library.catalog.service.CatalogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class CatalogDamageController {

    private final CatalogService catalogService;

    public CatalogDamageController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog/damage/request")
    public String damageRequestPage(HttpSession session, Model model) {
        model.addAttribute("circulationList", catalogService.listCirculationBooks());
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("currentApplicant", session.getAttribute("catalogAccountId"));
        return "catalog/catalog-damage-request";
    }

    @PostMapping("/catalog/damage/request/submit")
    public String submitDamageRequest(@RequestParam String isbn,
                                      @RequestParam String damageReason,
                                      @RequestParam String applicant,
                                      HttpSession session) {
        String currentApplicant = (String) session.getAttribute("catalogAccountId");
        catalogService.submitDamageRequest(
                isbn,
                damageReason,
                currentApplicant != null && !currentApplicant.isBlank() ? currentApplicant : applicant,
                LocalDate.now()
        );
        return "redirect:/catalog/damage/request";
    }

    @GetMapping("/catalog/damage/approve")
    public String approvePage(HttpSession session, Model model) {
        model.addAttribute("damageRequests", catalogService.listDamageRequests());
        model.addAttribute("currentOperator", session.getAttribute("catalogAccountId"));
        return "catalog/catalog-damage-approve";
    }

    @PostMapping("/catalog/damage/approve/submit")
    public String approve(@RequestParam String requestId,
                          @RequestParam String decision,
                          @RequestParam String operator,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        String currentOperator = (String) session.getAttribute("catalogAccountId");
        if (currentOperator == null || currentOperator.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "未获取到当前登录账号");
            return "redirect:/catalog/damage/approve";
        }
        if (operator == null || operator.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "请填写审核人");
            return "redirect:/catalog/damage/approve";
        }
        if (!currentOperator.equals(operator)) {
            redirectAttributes.addFlashAttribute("error", "审核人必须为当前登录账号");
            return "redirect:/catalog/damage/approve";
        }
        String applicant = catalogService.getDamageRequest(requestId).getApplicant();
        if (operator.equals(applicant)) {
            redirectAttributes.addFlashAttribute("error", "申请人不能审核自己的报损申请");
            return "redirect:/catalog/damage/approve";
        }
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
