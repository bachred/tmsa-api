package com.example.tmsaapi.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends BaseException {

    public AuthException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
    
}
