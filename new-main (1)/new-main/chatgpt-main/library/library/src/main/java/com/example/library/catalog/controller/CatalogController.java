package com.example.library.catalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    /**
     * 编目子系统首页
     */
    @GetMapping({"", "/", "/index"})
    public String catalogIndex() {
        return "catalog/index"; // 返回编目子系统目录页
    }

    /**
     * 返回系统首页
     */
    @GetMapping("/home")
    public String returnSystemHome() {
        return "index"; // 返回系统首页 templates/index.html
    }

}
