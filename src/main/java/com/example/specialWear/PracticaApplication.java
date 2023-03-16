package com.example.specialWear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class PracticaApplication {

    public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/upload";

    public static void main(String[] args) {
        new File(UPLOAD_DIR).mkdir();
        SpringApplication.run(PracticaApplication.class, args);
    }
}
