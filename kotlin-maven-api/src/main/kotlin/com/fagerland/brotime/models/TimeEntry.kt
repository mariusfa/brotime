package com.fagerland.brotime.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "timeEntry")
class TimeEntry(
        var startTime: Long?,
        var endTime: Long?,
        var timeZone: String?,

        @JsonIgnore
        @ManyToOne
        var userEntry: UserEntry,

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null
)