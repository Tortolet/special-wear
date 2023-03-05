package com.example.specialWear.controllers;

import com.example.specialWear.exceptions.DepartmentNotFound;
import com.example.specialWear.models.Departments;
import com.example.specialWear.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add_department")
    public ResponseEntity<Departments> addNewDepartment(@RequestBody Departments departments){
        return departmentService.addDepartment(departments);
    }

    @GetMapping("get_department_by_id")
    public ResponseEntity<Departments> getDepartmentById(@RequestHeader Long departmentId){
        Departments department = departmentService.findDepartmentsById(departmentId);
        if(department.getId() == null){
            throw new DepartmentNotFound("Цех не найден");
        }
        return ResponseEntity.ok().body(department);
    }

    @GetMapping("/get_departments")
    public ResponseEntity<List<Departments>> getAllDepartments(){
        return ResponseEntity.ok().body(departmentService.allDepartments());
    }
}
