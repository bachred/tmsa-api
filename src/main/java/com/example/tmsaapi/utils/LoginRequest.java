package com.example.tmsaapi.utils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull(message = "username is required")
    @NotEmpty(message = "username must not be empty")
    private String username;

    @NotNull(message = "password is required")
    @NotEmpty(message = "password must not be empty")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
