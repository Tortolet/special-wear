package com.example.specialWear.repos;

import com.example.specialWear.models.SpecialWears;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialWearsRepo extends JpaRepository<SpecialWears, Long> {
}
