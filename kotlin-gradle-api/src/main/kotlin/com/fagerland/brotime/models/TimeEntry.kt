package com.fagerland.brotime.models

class TimeEntry {

    var startTime: Long
    val endTime: Long

    var timeZone: String

    constructor(startTime: Long, endTime: Long, timeZone: String) {
        this.startTime = startTime
        this.endTime = endTime
        this.timeZone = timeZone
    }
}