package com.example.specialWear.services;

import com.example.specialWear.models.SpecialWears;
import com.example.specialWear.repos.SpecialWearRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialWearService {

    private final SpecialWearRepo specialWearRepo;

    public SpecialWearService(SpecialWearRepo specialWearRepo) {
        this.specialWearRepo = specialWearRepo;
    }

    public void save(SpecialWears specialWears){

        LocalDateTime startDate = LocalDateTime.now();
        specialWears.setDateStart(startDate);

        LocalDateTime endDate = LocalDateTime.now().plusMonths(specialWears.getWear_period_month());
        specialWears.setDateEnd(endDate);

        specialWearRepo.save(specialWears);
    }

    public SpecialWears findSpecialWearById(Long specialWearId) {
        Optional<SpecialWears> specialWears = specialWearRepo.findById(specialWearId);
        return specialWears.orElse(new SpecialWears());
    }

    public List<SpecialWears> allSpecialWears() {
        return specialWearRepo.findAll();
    }

    public ResponseEntity<SpecialWears> addSpecialWear(SpecialWears specialWears){
        this.save(specialWears);
        return ResponseEntity.ok().body(specialWears);
    }
}
