package com.thymemother.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/ssr/auto")
public class AutoController {
    @GetMapping
    public void index(Model model) {
        model.addAttribute("now", LocalDateTime.now().toString());
    }
}
