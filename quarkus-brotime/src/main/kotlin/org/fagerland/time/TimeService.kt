package org.fagerland.time

import org.fagerland.time.dto.TimeRequestDTO
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
        timeRepository.createTime(timeRequestDTO, user)
    }

    fun list(name: String): List<Time> {
        val user = userService.getUser(name)
        return timeRepository.listTimesByUser(user)
    }

    @Transactional
    fun update(timeRequestDTO: TimeRequestDTO, name: String, id: Long) {
        val user = userService.getUser(name)
        val time =
            timeRepository.find("id = ?1 and user = ?2", id, user).firstResult() ?: throw WebApplicationException(
                "Cannot find time: $id for user: ${user.id}",
                Response.Status.NOT_FOUND
            )
        timeRepository.updateTime(time, timeRequestDTO)
    }
}
