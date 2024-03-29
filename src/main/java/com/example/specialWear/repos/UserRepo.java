package com.example.specialWear.repos;

import com.example.specialWear.models.Employees;
import com.example.specialWear.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    Users findByEmployee(Employees employee);

}
