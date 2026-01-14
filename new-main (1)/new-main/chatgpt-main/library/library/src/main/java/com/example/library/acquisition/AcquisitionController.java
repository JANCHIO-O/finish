package com.example.library.acquisition;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/acquisition")
public class AcquisitionController {

    @GetMapping
    public String index() {
        return "acquisition/index";
    }

    @GetMapping("/collect")
    public String collect() { return "acquisition/collect"; }

    @GetMapping("/order")
    public String order() { return "acquisition/order"; }

    @GetMapping("/verify")
    public String verify() { return "acquisition/verify"; }

    @GetMapping("/modify")
    public String modify() { return "acquisition/modify"; }

    @GetMapping("/return")
    public String returnPage() { return "acquisition/return"; }
}
