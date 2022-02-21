package com.example.tmsaapi.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    
    public BaseException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatusCode();
}
