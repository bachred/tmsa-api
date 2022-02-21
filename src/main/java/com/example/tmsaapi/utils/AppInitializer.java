package com.example.tmsaapi.utils;

import com.example.tmsaapi.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {

    @Autowired
    private FileService fileService;

    @Override
    public void run(String... args) throws Exception {

        fileService.init();
    }

}
