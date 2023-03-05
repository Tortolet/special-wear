package com.example.specialWear.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DirectorDepartmentExist extends RuntimeException {
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public DirectorDepartmentExist(String message){
        super(message);
    }
}
