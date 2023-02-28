//package com.example.practica;
//
//import com.example.practica.models.Departments;
//import com.example.practica.models.Employee;
//import com.example.practica.repos.DepartmentRepo;
//import com.example.practica.repos.EmployeeRepo;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class Add { // проверка добавление работников
//
//    @Autowired
//    private DepartmentRepo departmentRepo;
//
//    @Autowired
//    private EmployeeRepo employeeRepo;
//
//    @Test
//    public void addAll(){
//        Departments departments = new Departments();
//        departments.setName("IT");
//        departmentRepo.save(departments);
//
//        Employee worker1 = new Employee();
//        worker1.setDepartments(departments);
//        worker1.setFirstName("Mike");
//        worker1.setLastName("Tyson");
//        worker1.setEmail("mike@gmail.com");
//        worker1.setPhone("+7-911-111-11-11");
//        employeeRepo.save(worker1);
//
//        Employee worker2 = new Employee();
//        worker2.setDepartments(departments);
//        worker2.setFirstName("Brad");
//        worker2.setLastName("Pitt");
//        worker2.setEmail("brad@gmail.com");
//        worker2.setPhone("+7-911-121-21-12");
//        employeeRepo.save(worker2);
//    }
//}
