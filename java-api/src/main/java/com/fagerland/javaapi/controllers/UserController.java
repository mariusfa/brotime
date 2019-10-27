package com.fagerland.javaapi.controllers;

import com.fagerland.javaapi.models.UserEntry;
import com.fagerland.javaapi.pojo.LoginForm;
import com.fagerland.javaapi.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(loginForm.getPassword());
            UserEntry newUserEntry = new UserEntry(loginForm.getUsername(), hashedPassword);
            userRepository.save(newUserEntry);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/api/user/signin")
    public ResponseEntity signinUser(@RequestBody LoginForm loginForm) {
        UserEntry userEntry = userRepository.findFirstByUsername(loginForm.getUsername());
        if (userEntry == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean passwordMatch = passwordEncoder.matches(loginForm.getPassword(), userEntry.getHashedPassword());

        if (passwordMatch && usernameMatch(loginForm, userEntry)) {
            String token = getJwtToken(userEntry);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    private boolean usernameMatch(@RequestBody LoginForm loginForm, UserEntry userEntry) {
        return userEntry.getUsername().equals(loginForm.getUsername());
    }

    @GetMapping("/api/refresh")
    public ResponseEntity refreshToken(Authentication authentication) {
        String username = authentication.getName();
        UserEntry userEntry = userRepository.findFirstByUsername(username);
        if (userEntry != null) {
            String token = getJwtToken(userEntry);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String getJwtToken(UserEntry userEntry) {
        Date now = new Date();
        long validMilliSeconds = 3600000;
        Date valid = new Date(now.getTime() + validMilliSeconds);

        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(userEntry.getUsername()))
                .setIssuedAt(now)
                .setExpiration(valid)
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }
}
