package com.example.specialWear;

import com.example.specialWear.models.Departments;
import com.example.specialWear.models.SpecialWears;
import com.example.specialWear.repos.DepartmentsRepo;
import com.example.specialWear.repos.SpecialWearsRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChecksSpecialWearTest {

//  @Autowired
//  private SpecialWearsRepo specialWearsRepo;
    @Autowired
    private DepartmentsRepo departmentsRepo;


//  @Test
//  public void TestWear(){
//      SpecialWears test = new SpecialWears();
//
//      test.setCost(500);
//      specialWearsRepo.save(test);
//  }

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
