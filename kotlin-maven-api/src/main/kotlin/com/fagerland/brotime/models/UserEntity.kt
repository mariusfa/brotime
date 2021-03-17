package com.fagerland.brotime.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="userEntry")
class UserEntity(
        var username: String?,
        var hashedPassword: String,
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null
)
