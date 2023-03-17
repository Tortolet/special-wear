package com.example.specialWear.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartFrontController {
    @GetMapping("/cart")
    public String viewCart(){
        return "cart";
    }

    @GetMapping("/success")
    public String successRequestCart(){
        return "success";
    }
}
