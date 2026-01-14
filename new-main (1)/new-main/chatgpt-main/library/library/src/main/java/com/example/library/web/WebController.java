package com.example.library.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {

    @GetMapping
    public String index() { return "web/index"; }

    @GetMapping("/notice")
    public String notice() { return "web/notice"; }

    @GetMapping("/query")
    public String query() { return "web/query"; }

    @GetMapping("/announce")
    public String announce() { return "web/announce"; }

    @GetMapping("/message")
    public String message() { return "web/message"; }

    @GetMapping("/overdue")
    public String overdue() { return "web/overdue"; }
}
