package com.example.specialWear.services;

import com.example.specialWear.exceptions.UserNotFound;
import com.example.specialWear.models.Roles;
import com.example.specialWear.models.Users;
import com.example.specialWear.repos.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepo;
        this.bCryptPasswordEncoder = passwordEncoder;
    }

    public Users findUserById(Long userId) {
        Optional<Users> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new Users());
    }

    public List<Users> allUsers() {
        return userRepository.findAll();
    }

    public void save(Users user){
        String enPas = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(enPas);
        this.userRepository.save(user);
    }

    public boolean registrationUser(Users user){
        Users userInBD = userRepository.findByUsername(user.getUsername());

        if(userInBD != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Roles.ROLE_USER));
        user.setAvatar("default_avatar.jpg");
        return true;
    }

    public Users getUserFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByUsername(auth.getName());
        if(user.getId() == null){
            throw new UserNotFound("Пользователь не найден");
        }
        return user;
    }
}
