package com.example.specialWear;

import com.example.specialWear.models.Departments;
import com.example.specialWear.repos.DepartmentsRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChecksSpecialWearTest {

    @Autowired
    private DepartmentsRepo departmentsRepo;

    @Test
    public void TestDep(){
        Departments test = new Departments();

        test.setName("ПАО СБЕР");
        test.setFirstNameDirector("Андрей");
        test.setSecondNameDirector("Андреевич");
        test.setLastNameDirector("Андреев");

        departmentsRepo.save(test);
    }
}
