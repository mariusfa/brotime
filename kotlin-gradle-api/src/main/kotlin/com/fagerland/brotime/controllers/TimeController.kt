package com.fagerland.brotime.controllers

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class TimeController {

    @GetMapping("/api/time")
    fun getTime(): String = "time"

    @GetMapping("/api/time/register")
    fun registerTime(): String = "register time"

    @PutMapping("/api/time/edit")
    fun editTime(): String = "edit time"
}