package com.example.specialWear.repos;

import com.example.specialWear.models.SpecialWears;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialWearRepo extends JpaRepository<SpecialWears, Long> {
}
