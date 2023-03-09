package com.example.specialWear.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserPasswordNotConfirm extends RuntimeException {
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public UserPasswordNotConfirm(String message){
        super(message);
    }
}
