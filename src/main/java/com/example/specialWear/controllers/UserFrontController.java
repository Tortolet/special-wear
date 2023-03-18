package com.example.specialWear.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserFrontController {

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @GetMapping("/profile")
    public String showProfile(){
        return "profile";
    }
}
