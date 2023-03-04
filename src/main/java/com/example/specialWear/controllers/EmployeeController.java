package com.example.specialWear.controllers;

import com.example.specialWear.exceptions.EmployeeNotFound;
import com.example.specialWear.models.Departments;
import com.example.specialWear.models.Employees;
import com.example.specialWear.services.DepartmentService;
import com.example.specialWear.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add_employee")
    public ResponseEntity<Employees> addNewEmployee(@RequestBody Employees employees, @RequestHeader Long depId) {
        Departments departments = departmentService.findDepartmentsById(depId);
        List<Employees> allEmployees = employeeService.allEmployees();

        return employeeService.addEmployee(employees, departments, allEmployees);
    }


    @GetMapping("/get_user_by_id")
    public ResponseEntity<?> getEmployeeById(@RequestHeader Long employeeId){
        if(employeeService.findEmployeesById(employeeId).getId() == null){
            throw new EmployeeNotFound("Работник не найден");
        }
        return ResponseEntity.ok().body(employeeService.findEmployeesById(employeeId));
    }
}
