package com.example.specialWear.controllers;

import com.example.specialWear.exceptions.UsageNotFound;
import com.example.specialWear.models.Employees;
import com.example.specialWear.models.SpecialWears;
import com.example.specialWear.models.Usages;
import com.example.specialWear.services.EmployeeService;
import com.example.specialWear.services.SpecialWearService;
import com.example.specialWear.services.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsageController {

    @Autowired
    private UsageService usageService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SpecialWearService specialWearService;

    @PostMapping("/add_usage")
    public ResponseEntity<Usages> addNewUsage(@RequestHeader Long employeeId, @RequestHeader Long specialWearId){
        Employees employee = employeeService.findEmployeesById(employeeId);
        SpecialWears specialWear = specialWearService.findSpecialWearById(specialWearId);

        return usageService.addNewUsage(employee, specialWear);
    }

    @GetMapping("/get_usage_by_id")
    public ResponseEntity<Usages> getUsageById(@RequestHeader Long usageId){
        Usages usage =  usageService.findUsageById(usageId);
        if(usage.getId() == null){
            throw new UsageNotFound("Получение не было найдено");
        }
        return ResponseEntity.ok().body(usage);
    }

    @GetMapping("/get_usages")
    public ResponseEntity<List<Usages>> getAllUsages(){
        return ResponseEntity.ok().body(usageService.allUsages());
    }
}
