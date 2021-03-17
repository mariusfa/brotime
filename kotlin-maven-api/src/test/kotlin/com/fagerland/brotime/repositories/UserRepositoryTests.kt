package com.fagerland.brotime.repositories

import com.fagerland.brotime.entities.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository
) {

    @Test
    fun `When findByUsername then return User`() {
        val fakeUser = UserEntity("FakeUser", "FakePasswordHash")
        entityManager.persist(fakeUser)
        entityManager.flush()
        val foundUser = userRepository.findFirstByUsername("FakeUser")
        val notFound = userRepository.findFirstByUsername("No User")

        assertThat(foundUser).isEqualTo(fakeUser)
        assertThat(notFound).isNull()
    }
}
