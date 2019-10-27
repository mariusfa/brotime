package com.fagerland.javaapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "timeEntry")
public class TimeEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long startStamp;
    private long endStamp;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userEntry_id", nullable = false)
    private UserEntry userEntry;

    public TimeEntry(long startStamp, long endStamp, UserEntry userEntry) {
        this.startStamp = startStamp;
        this.endStamp = endStamp;
        this.userEntry = userEntry;
    }

    public TimeEntry() {
    }

    public Long getId() {
        return id;
    }

    public UserEntry getUserEntry() {
        return userEntry;
    }

    public void setUserEntry(UserEntry userEntry) {
        this.userEntry = userEntry;
    }

    public long getStartStamp() {
        return startStamp;
    }

    public long getEndStamp() {
        return endStamp;
    }

    public void setEndStamp(long endStamp) {
        this.endStamp = endStamp;
    }
}
