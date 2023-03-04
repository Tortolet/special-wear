package com.example.specialWear.services;

import com.example.specialWear.models.Departments;
import com.example.specialWear.repos.DepartmentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentsRepo departmentsRepo;

    public DepartmentService(DepartmentsRepo departmentsRepo) {
        this.departmentsRepo = departmentsRepo;
    }

    public void save(Departments departments){
        departmentsRepo.save(departments);
    }

    public Departments findDepartmentsById(Long depId) {
        Optional<Departments> departments = departmentsRepo.findById(depId);
        return departments.orElse(new Departments());
    }

    public List<Departments> allDepartments() {
        return departmentsRepo.findAll();
    }
}
