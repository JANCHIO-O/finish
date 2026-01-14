package com.example.library.statistics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @GetMapping
    public String index() { return "statistics/index"; }

    @GetMapping("/stat")
    public String stat() { return "statistics/stat"; }

    @GetMapping("/print")
    public String print() { return "statistics/print"; }

    @GetMapping("/extract")
    public String extract() { return "statistics/extract"; }

    @GetMapping("/calc")
    public String calc() { return "statistics/calc"; }

    @GetMapping("/report")
    public String report() { return "statistics/report"; }
}
