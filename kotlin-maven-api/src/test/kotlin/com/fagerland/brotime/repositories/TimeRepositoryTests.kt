package com.fagerland.brotime.repositories

import com.fagerland.brotime.entities.TimeEntity
import com.fagerland.brotime.entities.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class TimeRepositoryTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val timeRepository: TimeRepository
) {

    val fakeUser = UserEntity("FakeUser", "FakeHash")

    @BeforeEach
    fun setup() {
        entityManager.persist(fakeUser)
        entityManager.flush()
    }

    @Test
    fun `When find all by user id return list`() {
        val numTimes = 10
        repeat(numTimes) {
            entityManager.persist(TimeEntity(0, 0, null, fakeUser))
        }
        entityManager.flush()

        val timeList = timeRepository.findAllByUserEntityIdOrderByStartTimeDesc(fakeUser.id)
        assertThat(timeList).hasSize(numTimes)
    }

    @Test
    fun `When find time by id and user id`() {
        val time = TimeEntity(0, 0, null, fakeUser)
        entityManager.persist(time)
        entityManager.flush()

        val timeFound = timeRepository.findFirstByIdAndUserEntityId(time.id, fakeUser.id)
        assertThat(timeFound).isEqualTo(time)
    }

    @Test
    fun `When find first time by descending order`() {
        entityManager.persist(TimeEntity(0, 0, null, fakeUser))
        val newestTime = TimeEntity(1, 1, null, fakeUser)
        entityManager.persist(newestTime)
        entityManager.flush()

        val timeFound = timeRepository.findFirstByUserEntityIdOrderByStartTimeDesc(fakeUser.id)
        assertThat(timeFound).isEqualTo(newestTime)
    }
}
