package com.example.tmsaapi.controller;

import javax.validation.Valid;

import com.example.tmsaapi.service.TokenService;
import com.example.tmsaapi.service.UserService;
import com.example.tmsaapi.utils.JwtResponse;
import com.example.tmsaapi.utils.LoginRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping
    public JwtResponse login(@Valid @RequestBody LoginRequest loginRequest) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        String token = tokenService.generateToken(userDetails.getUsername());

        JwtResponse response = new JwtResponse(token);

        return response;
    }

}
