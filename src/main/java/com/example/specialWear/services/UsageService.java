package com.example.specialWear.services;

import com.example.specialWear.exceptions.EmployeeNotFound;
import com.example.specialWear.exceptions.SpecialWearNotFound;
import com.example.specialWear.exceptions.UsageTimeIsEnd;
import com.example.specialWear.models.Employees;
import com.example.specialWear.models.SpecialWears;
import com.example.specialWear.models.Usages;
import com.example.specialWear.repos.UsagesRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsageService {

    private final UsagesRepo usagesRepo;

    public UsageService(UsagesRepo usagesRepo) {
        this.usagesRepo = usagesRepo;
    }

    public void save(Usages usages){

        LocalDateTime startDate = LocalDateTime.now();
        usages.setDateStartUsed(startDate);

        LocalDateTime endDate = usages.getSpecialWear().getDateEnd();
        usages.setDateStopUse(endDate);

        if(startDate.isAfter(endDate)){
            throw new UsageTimeIsEnd("Срок годности истек");
        }

        usagesRepo.save(usages);
    }

    public Usages findUsageById(Long usageId) {
        Optional<Usages> usage = usagesRepo.findById(usageId);
        return usage.orElse(new Usages());
    }

    public List<Usages> allUsages() {
        return usagesRepo.findAll();
    }

    public ResponseEntity<Usages> addNewUsage(Employees employees, SpecialWears specialWear){
        Usages usages = new Usages();

        if(employees.getId() == null){
            throw new EmployeeNotFound("Работник не найден");
        }
        usages.setEmployee(employees);

        if(specialWear.getId() == null){
            throw new SpecialWearNotFound("Спецодежда не найден");
        }
        usages.setSpecialWear(specialWear);


        usages.setEmployeeAgree(true);
        this.save(usages);
        return ResponseEntity.ok().body(usages);
    }
}
