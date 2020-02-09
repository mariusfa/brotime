package com.fagerland.brotime.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "timeEntry")
class TimeEntry(
        var startTime: Long?,
        var endTime: Long?,
        var timeZone: String?,

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "userEntry_id", nullable = false)
        var userEntry: UserEntry,

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null
)