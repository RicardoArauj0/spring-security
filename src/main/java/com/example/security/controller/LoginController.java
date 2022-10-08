package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("login")
    private String getLoginView() {
        return "login";
    }

    @GetMapping("courses")
    private String getCoursesView() {
        return "courses";
    }
}
