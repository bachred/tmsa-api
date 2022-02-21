package com.example.tmsaapi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.tmsaapi.exception.NotFoundException;
import com.example.tmsaapi.model.AppUser;
import com.example.tmsaapi.repository.UserRepository;
import com.example.tmsaapi.utils.RoleUser;
import com.example.tmsaapi.utils.UserRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public byte[] generateJsonPayload(Integer count) {

        try {

            List<AppUser> users = this.generateRandomUsers(count);

            byte[] jsonPayload = new ObjectMapper().writeValueAsBytes(users);

            return jsonPayload;

        } catch (JsonProcessingException ex) {

            throw new RuntimeException(ex);
        }

    }

    private List<AppUser> generateRandomUsers(Integer count) {

        List<AppUser> users = new ArrayList<AppUser>();

        Faker faker = new Faker(new Locale("fr"));

        for (int i = 0; i < count; i++) {

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            Date birthDate = faker.date().birthday();
            String city = faker.address().city();
            String country = faker.address().countryCode();
            String avatar = faker.avatar().image();
            String company = faker.company().name();
            String jobPosition = faker.job().position();
            String mobile = faker.phoneNumber().phoneNumber();
            String username = faker.name().username();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password(6, 10);
            RoleUser role = RoleUser.randomRole();

            AppUser user = new AppUser(firstName, lastName, birthDate, city, country, avatar, company, jobPosition,
                    mobile,
                    username, email, password, role);

            users.add(user);
        }

        return users;
    }

    public List<AppUser> parseJsonFile(MultipartFile file) {

        try {
            String filename = fileService.saveFile(file).toString();

            List<AppUser> users = fileService.parseContentFile(filename);

            return users;

        } catch (Exception e) {
            return null;
        }

    }

    public UserRecord createAllUsers(List<AppUser> users) {

        Integer userAdded = 0;
        Integer userskipped = 0;

        for (AppUser user : users) {

            if (this.userRepository.findFirstByUsernameOrEmail(user.getUsername(), user.getEmail()) == null) {

                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                this.userRepository.save(user);
                userAdded++;
            } else {
                userskipped++;
            }

        }

        return new UserRecord(userAdded, userskipped, users.size());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        AppUser user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("user not found");
        }

        return user;
    }

    public List<AppUser> getAllUsers() {

        return this.userRepository.findAll();
    }

}
