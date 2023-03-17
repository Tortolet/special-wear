package com.example.specialWear.repos;

import com.example.specialWear.models.SizeCount;
import com.example.specialWear.models.SpecialWears;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeCountRepo extends JpaRepository<SizeCount, Long> {
    List<SizeCount> findBySpecialWears(SpecialWears specialWears);

    SizeCount findBySpecialWearsAndSize(SpecialWears specialWears, String size);
}
