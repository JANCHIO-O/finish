package com.example.library.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class SystemController {

    @GetMapping
    public String index() { return "system/index"; }

    @GetMapping("/backup")
    public String backup() { return "system/backup"; }

    @GetMapping("/params")
    public String params() { return "system/params"; }

    @GetMapping("/log")
    public String log() { return "system/log"; }

    @GetMapping("/upgrade")
    public String upgrade() { return "system/upgrade"; }

    @GetMapping("/authority")
    public String authority() { return "system/authority"; }
}
