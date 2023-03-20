package com.example.specialWear.controllers;

import com.example.specialWear.models.Users;
import com.example.specialWear.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPanelFrontController {

    @Autowired
    private UserService userService;

    @GetMapping("/panel")
    public String adminPanel() {
        Users user = userService.getUserFromAuth();
        return "panel";
    }
}
