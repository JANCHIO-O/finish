package com.example.library.circulation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/circulation")
public class CirculationController {

    @GetMapping
    public String index() { return "circulation/index"; }

    @GetMapping("/borrow")
    public String borrow() { return "circulation/borrow"; }

    @GetMapping("/return")
    public String returnPage() { return "circulation/return"; }

    @GetMapping("/reserve")
    public String reserve() { return "circulation/reserve"; }

    @GetMapping("/overdue")
    public String overdue() { return "circulation/overdue"; }

    @GetMapping("/info")
    public String info() { return "circulation/info"; }
}
