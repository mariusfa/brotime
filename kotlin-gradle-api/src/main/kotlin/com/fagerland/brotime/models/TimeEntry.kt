package com.fagerland.brotime.models

class TimeEntry {

    var startTime: Long
    val endTime: Long

    constructor(startTime: Long, endTime: Long) {
        this.startTime = startTime
        this.endTime = endTime
    }
}