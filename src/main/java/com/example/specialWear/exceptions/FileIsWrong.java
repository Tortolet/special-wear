package com.example.specialWear.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FileIsWrong extends RuntimeException {
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public FileIsWrong(String message){
        super(message);
    }
}
