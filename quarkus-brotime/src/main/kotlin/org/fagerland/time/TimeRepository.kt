package org.fagerland.time

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Sort
import org.fagerland.time.dto.TimeRequestDTO
import org.fagerland.user.User
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TimeRepository : PanacheRepository<Time> {

    fun createTime(timeRequestDTO: TimeRequestDTO, user: User) {
        val time = Time()
        time.startTime = timeRequestDTO.start
        time.endTime = timeRequestDTO.end
        time.user = user
        persist(time)
    }

    fun updateTime(time: Time, timeRequestDTO: TimeRequestDTO) {
        time.startTime = timeRequestDTO.start
        time.endTime = timeRequestDTO.end
        persist(time)
    }

    fun listTimesByUser(user: User) = list("user", Sort.by("startTime").descending(), user)
}
