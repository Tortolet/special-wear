package com.example.specialWear.controllers;

import com.example.specialWear.exceptions.SpecialWearNotFound;
import com.example.specialWear.models.SpecialWears;
import com.example.specialWear.services.SpecialWearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SpecialWearController {

    @Autowired
    private SpecialWearService specialWearService;

    @PostMapping("/add_spacial_wear")
    public ResponseEntity<SpecialWears> addNewSpecialWear(@RequestBody SpecialWears specialWears){
        return specialWearService.addSpecialWear(specialWears);
    }

    @GetMapping("/get_wear_by_id")
    public ResponseEntity<SpecialWears> getEmployeeById(@RequestHeader Long specialWearId){
        SpecialWears specialWear =  specialWearService.findSpecialWearById(specialWearId);
        if(specialWear.getId() == null){
            throw new SpecialWearNotFound("Спецодежда не найден");
        }
        return ResponseEntity.ok().body(specialWear);
    }

    @GetMapping("/get_wears")
    public ResponseEntity<List<SpecialWears>> getAllEmployees(){
        return ResponseEntity.ok().body(specialWearService.allSpecialWears());
    }
}
