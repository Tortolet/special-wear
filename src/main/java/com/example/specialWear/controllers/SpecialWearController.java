package com.example.specialWear.controllers;

import com.example.specialWear.exceptions.SpecialWearNotFound;
import com.example.specialWear.exceptions.UserNotFound;
import com.example.specialWear.models.SizeCount;
import com.example.specialWear.models.SpecialWears;
import com.example.specialWear.models.Users;
import com.example.specialWear.repos.SizeCountRepo;
import com.example.specialWear.repos.SpecialWearRepo;
import com.example.specialWear.repos.UserRepo;
import com.example.specialWear.services.SpecialWearService;
import com.example.specialWear.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class SpecialWearController {

    @Autowired
    private SpecialWearService specialWearService;

    @Autowired
    private SpecialWearRepo specialWearRepo;

    @Autowired
    private SizeCountRepo sizeCountRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @PostMapping("/add_spacial_wear")
    public ResponseEntity<SpecialWears> addNewSpecialWear(@RequestBody SpecialWears specialWears){
        return specialWearService.addSpecialWear(specialWears);
    }

    @GetMapping("/get_wear_by_id")
    public ResponseEntity<SpecialWears> getEmployeeById(@RequestHeader Long specialWearId){
        SpecialWears specialWear =  specialWearService.findSpecialWearById(specialWearId);
        if(specialWear.getId() == null){
            throw new SpecialWearNotFound("Спецодежда не найден");
        }
        return ResponseEntity.ok().body(specialWear);
    }

    @GetMapping("/get_wears")
    public ResponseEntity<List<SpecialWears>> getAllEmployees(){
        return ResponseEntity.ok().body(specialWearService.allSpecialWears());
    }

    @GetMapping("/get_wear_size_count")
    public ResponseEntity<List<SizeCount>> getSizeCountByWear(@RequestHeader Long specialWearId){
        SpecialWears specialWear =  specialWearService.findSpecialWearById(specialWearId);
        if(specialWear.getId() == null){
            throw new SpecialWearNotFound("Спецодежда не найден");
        }
        return ResponseEntity.ok().body(sizeCountRepo.findBySpecialWears(specialWear));
    }

    @PostMapping("/add_to_cart")
    public ResponseEntity<String> addWearToCart(@RequestHeader Long specialWearId, @RequestHeader String size){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Users user = userRepo.findByUsername(auth.getName());
        SpecialWears wear = specialWearService.findSpecialWearById(specialWearId);
        SizeCount sizeCount = sizeCountRepo.findBySpecialWearsAndSize(wear, size);

        user.addWear(sizeCount);

        userRepo.save(user);

        return ResponseEntity.ok().body("Вы добавили " + wear.getWearName() + ". Пользователь: " + auth.getName() + ". Размером: " + sizeCount.getSize());
    }

    @DeleteMapping("/remove_item_from_cart")
    public ResponseEntity<String> removeWearFromCart(@RequestHeader Long specialWearId, @RequestHeader String size){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Users user = userRepo.findByUsername(auth.getName());
        SpecialWears wear = specialWearService.findSpecialWearById(specialWearId);
        SizeCount sizeCount = sizeCountRepo.findBySpecialWearsAndSize(wear, size);

        user.removeWear(sizeCount);

        userRepo.save(user);

        return ResponseEntity.ok().body("Вы убрали " + wear.getWearName() + ". Пользователь: " + auth.getName() + ". Размером: " + sizeCount.getSize());
    }

    @DeleteMapping("/remove_all_items")
    public ResponseEntity<String> removeAllWears(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Users user = userRepo.findByUsername(auth.getName());

        user.removeAll();

        userRepo.save(user);

        return ResponseEntity.ok().body("Вы убрали все из корзины");
    }

    @GetMapping("/get_cart_items")
    public ResponseEntity<Set<SizeCount>> getCartItems(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepo.findByUsername(auth.getName());

        Set<SizeCount> res = user.getCart();

        return ResponseEntity.ok().body(res);
    }
}
