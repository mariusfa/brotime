package com.fagerland.brotime.models

import javax.persistence.*

@Entity
@Table(name="userEntry")
data class UserEntry(
        var username: String?,
        var hashedPassword: String,
        @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null
)

