package com.example.tmsaapi.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


public class ExceptionDetails {

    //private String message;
    private List<String> errors;

    public List<String> getErrors() {
        return this.errors;
    }

    public void addErrors(String error) {
        this.errors.add(error);
    }

    @JsonFormat(shape = Shape.STRING,pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;


    public ExceptionDetails() {
        this.timestamp = new Date();
        this.errors = new ArrayList<String>();
    }

    public ExceptionDetails(String message) {
        this();
        this.addErrors(message);
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
}