package com.fagerland.javaapi.models;

import javax.persistence.*;

@Entity
@Table(name="userEntry")
public class UserEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String hashedPassword;

    public UserEntry() {
    }

    public UserEntry(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
}
