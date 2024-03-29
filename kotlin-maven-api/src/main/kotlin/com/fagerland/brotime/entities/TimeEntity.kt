package com.fagerland.brotime.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class TimeEntity(
    var startTime: Long?,
    var endTime: Long?,
    var timeZone: String?,

    @JsonIgnore
    @ManyToOne
    var userEntity: UserEntity,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
)
