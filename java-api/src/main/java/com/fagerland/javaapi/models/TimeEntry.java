package com.fagerland.javaapi.models;

public class TimeEntry {

    private final long id;
    private final long start;
    private final long end;

    public TimeEntry(long id, long start, long end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
