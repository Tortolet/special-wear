package com.example.specialWear.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeNotFound extends RuntimeException {
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public EmployeeNotFound(String message){
        super(message);
    }
}