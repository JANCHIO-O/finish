package com.example.library.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String index() { return "user/index"; }

    @GetMapping("/register")
    public String register() { return "user/register"; }

    @GetMapping("/login")
    public String login() { return "user/login"; }

    @GetMapping("/cancel")
    public String cancel() { return "user/cancel"; }

    @GetMapping("/info")
    public String info() { return "user/info"; }

    @GetMapping("/level")
    public String level() { return "user/level"; }

    @GetMapping("/record")
    public String record() { return "user/record"; }
}
