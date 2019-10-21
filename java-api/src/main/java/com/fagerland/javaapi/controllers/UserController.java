package com.fagerland.javaapi.controllers;

import com.fagerland.javaapi.models.UserEntry;
import com.fagerland.javaapi.pojo.LoginForm;
import com.fagerland.javaapi.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/api/user/register")
    public ResponseEntity registerUser(@RequestBody LoginForm loginForm) {
        UserEntry userEntry = userRepository.findFirstByUsername(loginForm.getUsername());
        if (userEntry == null) {
            UserEntry newUserEntry = new UserEntry(loginForm.getUsername());
            userRepository.save(newUserEntry);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
