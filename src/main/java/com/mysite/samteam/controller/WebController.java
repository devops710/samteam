package com.mysite.samteam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register") // 테스트를 위해 사용 안함
    public String showRegistrationPage() {
        return "register";
    }
}

