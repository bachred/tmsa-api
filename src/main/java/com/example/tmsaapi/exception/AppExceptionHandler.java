package com.example.tmsaapi.exception;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationFailureHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleAppException(BaseException ex) throws Exception {

        ExceptionDetails exceptionDetails = new ExceptionDetails(ex.getMessage());

        return new ResponseEntity<>(exceptionDetails, ex.getStatusCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = new ExceptionDetails();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError f : fieldErrors) {

            exceptionDetails.addErrors(f.getDefaultMessage());
        }

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        throw new AuthException("username or password is incorrect........");

    }

    // fallback method
    @ExceptionHandler(AuthenticationException.class) // exception handled
    public ResponseEntity<Object> handleExceptions(AuthenticationException ex) {

        ExceptionDetails exceptionDetails = new ExceptionDetails("user not found");

        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);

    }

}
