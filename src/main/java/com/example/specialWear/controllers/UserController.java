package com.example.specialWear.controllers;

import com.example.specialWear.exceptions.UserAlreadyExist;
import com.example.specialWear.exceptions.UserNotFound;
import com.example.specialWear.exceptions.UserPasswordNotConfirm;
import com.example.specialWear.models.CartHistory;
import com.example.specialWear.models.SpecialWears;
import com.example.specialWear.models.Users;
import com.example.specialWear.repos.CartHistoryRepo;
import com.example.specialWear.repos.UserRepo;
import com.example.specialWear.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartHistoryRepo cartHistoryRepo;

    @PostMapping("/registration")
    public ResponseEntity<Users> addNewUser(@RequestBody Users user){

        if (!user.getPassword().equals(user.getPasswordConfirm())){
            throw new UserPasswordNotConfirm("Пароли не совпадают");
        }

        if(!userService.registrationUser(user)){
            throw new UserAlreadyExist("Пользователь уже существует");
        }

        userService.save(user);

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/get_users")
    public ResponseEntity<List<Users>> getAllEmployees(){
        return ResponseEntity.ok().body(userService.allUsers());
    }

    @GetMapping("/get_user")
    public ResponseEntity<Users> getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepo.findByUsername(auth.getName());
        if(user.getId() == null){
            throw new UserNotFound("Пользователь не найден");
        }

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/get_user_cart_history")
    public ResponseEntity<List<CartHistory>> getUserCartHistory(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepo.findByUsername(auth.getName());
        if(user.getId() == null){
            throw new UserNotFound("Пользователь не найден");
        }

        List<CartHistory> cartHistory = cartHistoryRepo.findByUser(user);

        return ResponseEntity.ok().body(cartHistory);
    }
}
