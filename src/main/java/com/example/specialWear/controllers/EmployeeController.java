package com.example.specialWear.controllers;

import com.example.specialWear.exceptions.DepartmentNotFound;
import com.example.specialWear.exceptions.EmployeeNotFound;
import com.example.specialWear.models.Departments;
import com.example.specialWear.models.Employees;
import com.example.specialWear.models.Users;
import com.example.specialWear.repos.EmployeesRepo;
import com.example.specialWear.repos.UserRepo;
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
    @Autowired
    private EmployeesRepo employeesRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/add_employee")
    public ResponseEntity<Employees> addNewEmployee(@RequestBody Employees employees, @RequestHeader Long depId) {
        Departments departments = departmentService.findDepartmentsById(depId);
        List<Employees> allEmployees = employeeService.allEmployees();

        return employeeService.addEmployee(employees, departments, allEmployees);
    }

    @GetMapping("/get_employee_by_id")
    public ResponseEntity<Employees> getEmployeeById(@RequestHeader Long employeeId){
        Employees employee =  employeeService.findEmployeesById(employeeId);
        if(employee.getId() == null){
            throw new EmployeeNotFound("Работник не найден");
        }
        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/get_employees")
    public ResponseEntity<List<Employees>> getAllEmployees(){
        return ResponseEntity.ok().body(employeeService.allEmployees());
    }

    @PutMapping("/update_employee")
    public ResponseEntity<Employees> editEmployee(@RequestHeader Long depId, @RequestHeader Long employeeId, @RequestBody Employees employees) {
        Employees emp = employeeService.findEmployeesById(employeeId);
        if(emp.getId() == null){
            throw new EmployeeNotFound("Сотрудник не найден");
        }

        Departments departments = departmentService.findDepartmentsById(depId);
        if(departments.getId() == null) {
            throw new DepartmentNotFound("Цех не найден");
        }
        emp.setEmail(employees.getEmail());
        emp.setFirstName(employees.getFirstName());
        emp.setSecondName(employees.getSecondName());
        emp.setLastName(employees.getLastName());
        emp.setPosts(employees.getPosts());
        emp.setSale(employees.getSale());
        emp.setDepartments(departments);
        employeesRepo.save(emp);

        return ResponseEntity.ok().body(emp);
    }

    @DeleteMapping("/delete_employee")
    public ResponseEntity<String> removeEmployee(@RequestHeader Long employeeId) {
        Employees emp = employeeService.findEmployeesById(employeeId);
        if(emp.getId() == null){
            throw new EmployeeNotFound("Сотрудник не найден");
        }
        Users user = userRepo.findByEmployee(emp);
        user.getRoles().removeAll(user.getRoles());
        userRepo.delete(user);

        emp.getPosts().removeAll(emp.getPosts());
        employeesRepo.delete(emp);

        return ResponseEntity.ok().body("Сотрудник: " + emp.getLastName() + " " + emp.getFirstName() + " " + emp.getSecondName() + " удален");
    }
}
