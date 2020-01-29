package com.fagerland.brotime.controllers

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class HealthController {

    @GetMapping("/healthy")
    fun health(): String = "healthy"
}