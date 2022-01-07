package org.fagerland.time

import org.fagerland.user.User
import org.fagerland.user.UserRepository
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

@ApplicationScoped
class TimeService(
    private val timeRepository: TimeRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun create(timeDTO: TimeDTO, name: String) {
        val user = userRepository.findByUsername(name) ?: throw WebApplicationException("User: $name no found", Response.Status.NOT_FOUND)
        val time = mapToTime(timeDTO, user)
        timeRepository.persist(time)
    }

    private fun mapToTime(timeDTO: TimeDTO, user: User): Time {
        val newTime = Time()
        newTime.startTime = timeDTO.start
        newTime.endTime = timeDTO.end
        newTime.user = user
        return newTime
    }
}
