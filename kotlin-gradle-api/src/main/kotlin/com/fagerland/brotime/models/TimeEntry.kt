package com.fagerland.brotime.models

class TimeEntry {

    var startTime: Long
    val endTime: Long

    var timeZone: String
    val id: String

    constructor(startTime: Long, endTime: Long, timeZone: String, id: String) {
        this.startTime = startTime
        this.endTime = endTime
        this.timeZone = timeZone
        this.id = id
    }
}