package com.example.practica.repos;

import com.example.practica.models.SpecialWears;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialWearsRepo extends JpaRepository<SpecialWears, Long> {
}
