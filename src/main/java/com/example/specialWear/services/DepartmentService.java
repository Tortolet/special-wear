package com.example.specialWear.services;

import com.example.specialWear.exceptions.DepartmentEmptyFields;
import com.example.specialWear.exceptions.DepartmentExist;
import com.example.specialWear.exceptions.DirectorDepartmentExist;
import com.example.specialWear.models.Departments;
import com.example.specialWear.repos.DepartmentsRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentsRepo departmentsRepo;

    public DepartmentService(DepartmentsRepo departmentsRepo) {
        this.departmentsRepo = departmentsRepo;
    }

    // TODO : Добавить в users при сохранении, как и сотрудников
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

    public ResponseEntity<Departments> addDepartment(Departments departments){
        List<Departments> allDepartments = allDepartments();

        if(departments.getName().isEmpty()
                || departments.getFirstNameDirector().isEmpty()
                || departments.getSecondNameDirector().isEmpty()
                || departments.getLastNameDirector().isEmpty()) {
            throw new DepartmentEmptyFields("Некоторые поля незаполненны");
        }

        for(var allDepart : allDepartments){
            if(Objects.equals(allDepart.getName(), departments.getName())){
                throw new DepartmentExist("Данный цех уже существует");
            }

            if(Objects.equals(allDepart.getFirstNameDirector(), departments.getFirstNameDirector())
                    && Objects.equals(allDepart.getSecondNameDirector(), departments.getSecondNameDirector())
                    && Objects.equals(allDepart.getLastNameDirector(), departments.getLastNameDirector())){
                throw new DirectorDepartmentExist("Директор уже существует");
            }
        }

        this.save(departments);
        return ResponseEntity.ok().body(departments);
    }
}
