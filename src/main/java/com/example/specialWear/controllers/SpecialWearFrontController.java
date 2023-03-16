package com.example.specialWear.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpecialWearFrontController {

    @GetMapping("/products")
    public String allWears(){
        return "products";
    }

    @GetMapping("/products/product")
    public String wear(@RequestParam Long id){
        return "product";
    }
}
