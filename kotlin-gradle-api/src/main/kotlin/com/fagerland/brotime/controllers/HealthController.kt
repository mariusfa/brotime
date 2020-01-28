package com.fagerland.brotime.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

    @GetMapping("/healthy")
    fun health(): String = "healthy"
}