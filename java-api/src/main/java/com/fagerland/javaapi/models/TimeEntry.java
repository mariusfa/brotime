package com.fagerland.javaapi.models;

import javax.persistence.*;

@Entity
@Table(name = "time_entry")
public class TimeEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long startStamp;
    private long endStamp;

    public TimeEntry() {
    }

    public TimeEntry(long startStamp, long endStamp) {
        this.startStamp = startStamp;
        this.endStamp = endStamp;
    }

    public Long getId() {
        return id;
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
