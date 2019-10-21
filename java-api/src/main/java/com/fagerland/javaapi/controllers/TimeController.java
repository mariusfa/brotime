package com.fagerland.javaapi.controllers;

import com.fagerland.javaapi.models.TimeEntry;
import com.fagerland.javaapi.repositories.TimeRepository;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class TimeController {

    private final TimeRepository repository;

    private final SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

    public TimeController(TimeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/time")
    public List<TimeEntry> getTime() {
        return repository.findAll();
    }

    @GetMapping("/api/time/register")
    public void registerTime() {
        TimeEntry oldTimeEntry = repository.findFirstByOrderByStartStampDesc();
        Date currentDate = new Date();
        if (oldTimeEntry == null) {
            TimeEntry timeEntry = new TimeEntry(currentDate.getTime(), currentDate.getTime());
            repository.save(timeEntry);
            return;
        }
        Date oldDate = new Date(oldTimeEntry.getStartStamp());
        if (isSameDay(currentDate, oldDate)) {
            oldTimeEntry.setEndStamp(currentDate.getTime());
            repository.save(oldTimeEntry);
        } else {
            TimeEntry timeEntry = new TimeEntry(currentDate.getTime(), currentDate.getTime());
            repository.save(timeEntry);
        }
    }

    private boolean isSameDay(Date currentDate, Date oldDate) {
        return fmt.format(currentDate).equals(fmt.format(oldDate));
    }

    private void populateTimeList(List<TimeEntry> timeList) {
        for (int i = 0; i < 10; i++) {
            long timeStart = (new Date()).getTime() - 14400000;
            long timeEnd = (new Date()).getTime();
            timeList.add(new TimeEntry(timeStart, timeEnd));
        }
    }
}
