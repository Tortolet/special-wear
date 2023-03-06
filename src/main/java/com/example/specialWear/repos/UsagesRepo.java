package com.example.specialWear.repos;

import com.example.specialWear.models.Usages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsagesRepo extends JpaRepository<Usages, Long> {
}
