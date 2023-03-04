package com.example.specialWear.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DepartmentNotFound extends RuntimeException {
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public DepartmentNotFound(String message){
        super(message);
    }
}