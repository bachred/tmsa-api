package com.example.tmsaapi.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.example.tmsaapi.model.AppUser;
import com.example.tmsaapi.service.UserService;
import com.example.tmsaapi.utils.UserRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService usersService;

    @GetMapping(value = "/generate")
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateUsers(@RequestParam Integer count) {

        byte[] jsonPayload = this.usersService.generateJsonPayload(count);

        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=users.json")
                .contentLength(jsonPayload.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new ByteArrayInputStream(jsonPayload)));

    }

    @PostMapping(value = "/batch")
    public ResponseEntity<UserRecord> upload(@RequestPart(required = true) @RequestParam("file") MultipartFile file) {

        List<AppUser> users = this.usersService.parseJsonFile(file);

        UserRecord userRecord = this.usersService.createAllUsers(users);

        return new ResponseEntity<>(userRecord, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<AppUser>> getAllUsers() {

        List<AppUser> users = this.usersService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<String> me() {

        String jsonString = "{'message':'profile page'}";

        return new ResponseEntity<>(jsonString, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{username}")
    public ResponseEntity<String> profil(@PathVariable String username) {

        String jsonString = String.format("{'message':'profile page of %s'}", username);

        return new ResponseEntity<>(jsonString, HttpStatus.OK);
    }
}
