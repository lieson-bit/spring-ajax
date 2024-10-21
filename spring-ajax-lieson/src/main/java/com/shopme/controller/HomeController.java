package com.shopme.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Roles: " + auth.getAuthorities());
        return "home"; // The view name is 'home', Spring will look for home.html in the templates folder
    }
}
