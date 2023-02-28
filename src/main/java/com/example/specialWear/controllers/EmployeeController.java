package com.example.specialWear.controllers;

import com.example.specialWear.models.Employees;
import com.example.specialWear.repos.EmployeesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeesRepo employeesRepo;

    @PostMapping("/add_employee")
    public ResponseEntity<Employees> addNewEmployee(@RequestBody Employees employees){
        // TODO: Перехватить исключения и ошибки

        employeesRepo.save(employees);
        return ResponseEntity.ok().body(employees);
    }


}
