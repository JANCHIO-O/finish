package com.example.library.periodical;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/periodical")
public class PeriodicalController {

    @GetMapping
    public String index() { return "periodical/index"; }

    @GetMapping("/visit")
    public String visit() { return "periodical/visit"; }

    @GetMapping("/order")
    public String order() { return "periodical/order"; }

    @GetMapping("/verify")
    public String verify() { return "periodical/verify"; }

    @GetMapping("/bind")
    public String bind() { return "periodical/bind"; }

    @GetMapping("/query")
    public String query() { return "periodical/query"; }
}
