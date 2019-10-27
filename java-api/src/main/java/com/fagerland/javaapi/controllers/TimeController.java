package com.fagerland.javaapi.controllers;

import com.fagerland.javaapi.models.TimeEntry;
import com.fagerland.javaapi.models.UserEntry;
import com.fagerland.javaapi.repositories.TimeRepository;
import com.fagerland.javaapi.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class TimeController {

    Logger logger = LoggerFactory.getLogger(TimeController.class);

    private final TimeRepository timeRepository;
    private final UserRepository userRepository;

    private final SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

    public TimeController(TimeRepository timeRepository, UserRepository userRepository) {
        this.timeRepository = timeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/time")
    public List<TimeEntry> getTime(Authentication authentication) {
        String name = authentication.getName();
        UserEntry userEntry = userRepository.findFirstByUsername(name);
        return timeRepository.findAllByUserEntryId(userEntry.getId());
    }

    @GetMapping("/api/time/register")
    public void registerTime(Authentication authentication) {
        String username = authentication.getName();
        UserEntry userEntry = userRepository.findFirstByUsername(username);
        TimeEntry oldTimeEntry = timeRepository.findFirstByUserEntryIdOrderByStartStampDesc(userEntry.getId());
        Date currentDate = new Date();
        if (oldTimeEntry == null) {
            TimeEntry timeEntry = new TimeEntry(currentDate.getTime(), currentDate.getTime(), userEntry);
            timeRepository.save(timeEntry);
            return;
        }
        Date oldDate = new Date(oldTimeEntry.getStartStamp());
        if (isSameDay(currentDate, oldDate)) {
            oldTimeEntry.setEndStamp(currentDate.getTime());
            timeRepository.save(oldTimeEntry);
        } else {
            TimeEntry timeEntry = new TimeEntry(currentDate.getTime(), currentDate.getTime(), userEntry);
            timeRepository.save(timeEntry);
        }
    }

    private boolean isSameDay(Date currentDate, Date oldDate) {
        return fmt.format(currentDate).equals(fmt.format(oldDate));
    }
}
