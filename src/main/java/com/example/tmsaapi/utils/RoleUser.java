package com.example.tmsaapi.utils;

import java.util.Random;

public enum RoleUser {

    ADMIN, USER;

    public static final RoleUser randomRole() {

        return RoleUser.values()[new Random().nextInt(RoleUser.values().length)];
    }
}
