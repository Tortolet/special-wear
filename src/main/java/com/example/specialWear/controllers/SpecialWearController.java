package com.example.specialWear.controllers;

import com.example.specialWear.models.SpecialWears;
import com.example.specialWear.services.SpecialWearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SpecialWearController {

    @Autowired
    private SpecialWearService specialWearService;

    @PostMapping("/add_spacial_wear")
    public ResponseEntity<SpecialWears> addNewSpecialWear(@RequestBody SpecialWears specialWears){
        return specialWearService.addSpecialWear(specialWears);
    }
}
