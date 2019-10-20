package com.fagerland.javaapi.models;

import javax.persistence.*;

@Entity
public class TimeEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long start;
    private long end;

    public TimeEntry() {
    }

    public TimeEntry(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
