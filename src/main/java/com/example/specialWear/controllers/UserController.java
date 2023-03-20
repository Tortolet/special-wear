package com.example.specialWear.controllers;

import com.example.specialWear.exceptions.FileIsWrong;
import com.example.specialWear.exceptions.UserAlreadyExist;
import com.example.specialWear.exceptions.UserPasswordNotConfirm;
import com.example.specialWear.exceptions.UsernameEmptyField;
import com.example.specialWear.models.CartHistory;
import com.example.specialWear.models.Users;
import com.example.specialWear.repos.CartHistoryRepo;
import com.example.specialWear.repos.UserRepo;
import com.example.specialWear.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/upload";

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
        Users user = userService.getUserFromAuth();

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/get_user_cart_history")
    public ResponseEntity<List<CartHistory>> getUserCartHistory(){
        Users user = userService.getUserFromAuth();

        List<CartHistory> cartHistory = cartHistoryRepo.findByUser(user);

        return ResponseEntity.ok().body(cartHistory);
    }

    @PutMapping("/update_user_info")
    public ResponseEntity<Users> editUserInfoWithoutPassword(@RequestHeader String username, @RequestBody(required = false) String about){
        Users user = userService.getUserFromAuth();

        if(username.isEmpty()){
            throw new UsernameEmptyField("Логин не заполнен");
        }

        user.setUsername(username);

        if(about == null){
            user.setInfo("Пусто");
        }

        if(about != null){
            user.setInfo(about);
        }

        userRepo.save(user);

        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update_user_password")
    public ResponseEntity<String> editUserPassword(@RequestHeader String oldPassword, @RequestHeader String newPassword, @RequestHeader String newPasswordConfirm) {
        Users user = userService.getUserFromAuth();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldPassword, user.getPassword())) {
            if (Objects.equals(newPassword, newPasswordConfirm)) {
                user.setPassword(newPassword);
                userService.save(user);

                return ResponseEntity.ok().body("Вы успешно изменили пароль у пользователя: " + user.getUsername());
            } else {
                return ResponseEntity.badRequest().body("Пароли не совпадают");
            }
        } else {
            return ResponseEntity.badRequest().body("Старый пароль неверен");

        }

    }

    @PutMapping("/update_user_avatar")
    public ResponseEntity<Users> editUserAvatar(@RequestBody MultipartFile avatar) {
        Users user = userService.getUserFromAuth();

        if(avatar == null){
            throw new FileIsWrong("Файл пуст");
        }

        String uuid = UUID.randomUUID().toString();
        String name = avatar.getOriginalFilename();
        String resultPath = uuid.concat(Objects.requireNonNull(name).substring(name.lastIndexOf(".")));

        Path fileNameAndPath = Paths.get(UPLOAD_DIR, resultPath);
        user.setAvatar(resultPath);

        try{
            if(!Objects.requireNonNull(avatar.getOriginalFilename()).isEmpty() && Objects.equals(avatar.getContentType(), "image/png") || Objects.equals(avatar.getContentType(), "image/jpeg")) {
                Files.write(fileNameAndPath, avatar.getBytes());
            } else throw new FileIsWrong("Неверный формат файла");
            if(!user.getAvatar().isEmpty()) {
                userRepo.save(user);
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new FileIsWrong("Выбран не тот файл");
        }

        return ResponseEntity.ok().body(user);
    }

}
