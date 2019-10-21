package com.fagerland.javaapi.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="userEntry")
public class UserEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "userEntry")
    private List<TimeEntry> timeEntries;

    public UserEntry() {
    }

    public UserEntry(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }
}