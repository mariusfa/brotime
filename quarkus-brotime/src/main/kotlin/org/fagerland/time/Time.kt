package org.fagerland.time

import org.fagerland.time.dto.TimeDTO
import org.fagerland.user.User
import java.math.BigInteger
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "time", schema = "public")
class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    lateinit var startTime: BigInteger
    lateinit var endTime: BigInteger

    @ManyToOne
    lateinit var user: User

    fun toDTO(): TimeDTO = TimeDTO(
        id = this.id!!,
        start = this.startTime,
        end = this.endTime
    )
}