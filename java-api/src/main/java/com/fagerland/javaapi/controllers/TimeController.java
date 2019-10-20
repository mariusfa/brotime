package com.fagerland.javaapi.controllers;

import com.fagerland.javaapi.models.TimeEntry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class TimeController {

    @GetMapping("/api/time")
    public List<TimeEntry> getTime() {
        List<TimeEntry> timeList = new ArrayList<>();
        populateTimeList(timeList);
        return timeList;
    }

    private void populateTimeList(List<TimeEntry> timeList) {
        for (int i = 0; i < 10; i++) {
            long timeStart = (new Date()).getTime() - 14400000;
            long timeEnd = (new Date()).getTime();
            timeList.add(new TimeEntry(i, timeStart, timeEnd));
        }
    }
}
