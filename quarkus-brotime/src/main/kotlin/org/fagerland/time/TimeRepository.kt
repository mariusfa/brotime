package org.fagerland.time

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.fagerland.time.dto.TimeRequestDTO
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class TimeRepository : PanacheRepository<Time> {

    fun updateTime(time: Time, timeRequestDTO: TimeRequestDTO) {
        time.startTime = timeRequestDTO.start
        time.endTime = timeRequestDTO.end
        persist(time)
    }
}
