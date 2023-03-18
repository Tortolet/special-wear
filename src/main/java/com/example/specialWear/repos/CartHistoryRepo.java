package com.example.specialWear.repos;

import com.example.specialWear.models.CartHistory;
import com.example.specialWear.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartHistoryRepo extends JpaRepository<CartHistory, Long> {
    List<CartHistory> findByUser(Users user);
}