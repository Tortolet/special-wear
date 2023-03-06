package com.example.specialWear.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsageTimeIsEnd extends RuntimeException {
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public UsageTimeIsEnd(String message){
        super(message);
    }
}
