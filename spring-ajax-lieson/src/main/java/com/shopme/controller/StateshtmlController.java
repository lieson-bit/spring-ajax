package com.shopme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StateshtmlController {
    @GetMapping("/stateshtml")
    public String stateshtml() {
        return "stateshtml"; // The view name is 'states', Spring will look f
    }
}
