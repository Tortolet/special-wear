package com.example.specialWear.services;

import com.example.specialWear.exceptions.DepartmentNotFound;
import com.example.specialWear.exceptions.EmployeeExist;
import com.example.specialWear.exceptions.EmployeeIsEmptyFields;
import com.example.specialWear.exceptions.UserAlreadyExist;
import com.example.specialWear.models.Departments;
import com.example.specialWear.models.Employees;
import com.example.specialWear.models.Users;
import com.example.specialWear.repos.EmployeesRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeesRepo employeesRepo;

    private final UserService userService;

    public EmployeeService(EmployeesRepo employeesRepo, UserService userService) {
        this.employeesRepo = employeesRepo;
        this.userService = userService;
    }

    public void save(Employees employees){
        employeesRepo.save(employees);
    }

    public Employees findEmployeesById(Long employeeId) {
        Optional<Employees> employees = employeesRepo.findById(employeeId);
        return employees.orElse(new Employees());
    }

    public List<Employees> allEmployees() {
        return employeesRepo.findAll();
    }

    public ResponseEntity<Employees> addEmployee(Employees employees, Departments departments, List<Employees> allEmployees) {
        if(departments.getId() == null){
            throw new DepartmentNotFound("Цех не найден");
        }

        employees.setDepartments(departments);

        if(employees.getFirstName().isEmpty()
                || employees.getSecondName().isEmpty()
                || employees.getLastName().isEmpty()
                || employees.getEmail().isEmpty()){
            throw new EmployeeIsEmptyFields("Некоторые поля незаполненны");
        }

        for (var allEmployee : allEmployees) {
            if (Objects.equals(allEmployee.getFirstName(), employees.getFirstName())
                    && Objects.equals(allEmployee.getSecondName(), employees.getSecondName())
                    && Objects.equals(allEmployee.getLastName(), employees.getLastName())
                    || Objects.equals(allEmployee.getEmail(), employees.getEmail())) {
                throw new EmployeeExist("Работник уже существует");
            }
        }

        // 1. Вариант без связей

        Users user = new Users();

        String username = employees.getEmail().substring(0, employees.getEmail().indexOf("@"));
        UUID password = UUID.randomUUID();
        user.setUsername(username);
        user.setPassword(password.toString());

        if(!userService.registrationUser(user)){
            throw new UserAlreadyExist("Пользователь уже существует");
        }

        userService.save(user);
        System.out.println(password);

        this.save(employees);
        return ResponseEntity.ok().body(employees);
    }
}
