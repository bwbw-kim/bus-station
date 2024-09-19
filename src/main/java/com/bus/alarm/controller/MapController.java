package com.bus.alarm.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class MapController {
    @GetMapping
    public String index(HttpServletResponse response) {
        return "index";
    }
}

