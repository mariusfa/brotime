package org.fagerland.user

import io.quarkus.security.jpa.Password
import io.quarkus.security.jpa.Username
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user", schema = "public")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Username
    lateinit var username: String
    @Password
    lateinit var hashedPassword: String
}