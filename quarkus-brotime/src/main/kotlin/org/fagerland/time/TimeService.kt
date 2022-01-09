package org.fagerland.time

import io.quarkus.panache.common.Sort
import org.fagerland.time.dto.TimeRequestDTO
import org.fagerland.user.User
import org.fagerland.user.UserService
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

@ApplicationScoped
class TimeService(
    private val timeRepository: TimeRepository,
    private val userService: UserService,
) {

    @Transactional
    fun create(timeRequestDTO: TimeRequestDTO, name: String) {
        val user = userService.getUser(name)
        val time = mapToTime(timeRequestDTO, user)
        timeRepository.persist(time)
    }

    fun list(name: String): List<Time> {
        val user = userService.getUser(name)
        return timeRepository.list("user", Sort.by("startTime").descending(), user)
    }

    private fun mapToTime(timeRequestDTO: TimeRequestDTO, user: User): Time {
        val newTime = Time()
        newTime.startTime = timeRequestDTO.start
        newTime.endTime = timeRequestDTO.end
        newTime.user = user
        return newTime
    }

    @Transactional
    fun update(timeRequestDTO: TimeRequestDTO, name: String, id: Long) {
        val user = userService.getUser(name)
        val time =
            timeRepository.find("id = ?1 and user = ?2", id, user).firstResult() ?: throw WebApplicationException(
                "Cannot find time: $id for user: $name",
                Response.Status.NOT_FOUND
            )
        timeRepository.updateTime(time, timeRequestDTO)
    }
}
