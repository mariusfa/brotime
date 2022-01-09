package org.fagerland.time

import io.quarkus.panache.common.Sort
import org.fagerland.time.dto.CreateTimeDTO
import org.fagerland.user.User
import org.fagerland.user.UserService
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class TimeService(
    private val timeRepository: TimeRepository,
    private val userService: UserService,
) {

    @Transactional
    fun create(createTimeDTO: CreateTimeDTO, name: String) {
        val user = userService.getUser(name)
        val time = mapToTime(createTimeDTO, user)
        timeRepository.persist(time)
    }

    fun list(name: String): List<Time> {
        val user = userService.getUser(name)
        return timeRepository.list("user", Sort.by("startTime").descending(), user)
    }

    private fun mapToTime(createTimeDTO: CreateTimeDTO, user: User): Time {
        val newTime = Time()
        newTime.startTime = createTimeDTO.start
        newTime.endTime = createTimeDTO.end
        newTime.user = user
        return newTime
    }
}
