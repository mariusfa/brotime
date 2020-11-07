package com.fagerland.brotime.dto.requests

data class updateTimeDTO(
    val startTime: Long,
    val endTime: Long,
    val timeZone: String,
    val id: Long
)
