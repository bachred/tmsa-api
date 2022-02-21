package com.example.tmsaapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/")
@ApiIgnore
public class HomeController {

    @GetMapping(value = { "", "/" })
    public ResponseEntity<String> me() {

        return new ResponseEntity<>("index page", HttpStatus.OK);
    }

}
