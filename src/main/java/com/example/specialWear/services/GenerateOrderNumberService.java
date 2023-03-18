package com.example.specialWear.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GenerateOrderNumberService {

    public String getOrderNumber(){
        Random I  = new Random();
        List<Integer> list = new ArrayList<Integer>();
        int number;

        StringBuilder temp = new StringBuilder();

        for(int counter = 1; counter <= 5; counter++){
            number = I.nextInt(100);
            while(list.contains(number)) {
                number = I.nextInt(100);
            }
            list.add(number);
        }

        for (Integer integer : list) {
            temp.append(integer);
        }

        return temp.toString();
    }
}
