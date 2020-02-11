package com.fagerland.brotime.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "timeEntry")
class TimeEntry(
        var startTime: Long?,
        var endTime: Long?,
        var timeZone: String?,

        @ManyToOne
        var userEntry: UserEntry,

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null
)