package com.tasktrackr.tasktrackr.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/tasks";
    }
}
