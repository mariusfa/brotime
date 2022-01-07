package org.fagerland.user

import io.quarkus.elytron.security.common.BcryptUtil
import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.fagerland.time.TimeRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.wildfly.common.Assert
import java.math.BigInteger
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.core.MediaType

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TimeResourceTest {

    @Inject
    private lateinit var userRepository: UserRepository

    @Inject
    private lateinit var userService: UserService

    @Inject
    private lateinit var timeRepository: TimeRepository

    @BeforeAll
    @Transactional
    fun setup() {
        val testUser = User()
        testUser.username = "test_user"
        testUser.hashedPassword = BcryptUtil.bcryptHash("test_password")
        userRepository.persistAndFlush(testUser)
    }

    @AfterAll
    @Transactional
    fun tearDown() {
        timeRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @Transactional
    fun `should post time entry`() {
        val token = userService.loginUser(UserDTO(username = "test_user", "test_password"))
        Given {
            header("Content-Type", MediaType.APPLICATION_JSON)
            header("Authorization", "Bearer $token")
            body(
                """
                {
                    "start": 1641593434076,
                    "end": 1641593434076
                }
            """.trimIndent()
            )
        } When {
            post("/api/time")
        } Then {
            statusCode(201)
        }

        val time = timeRepository.findById(1)
        Assertions.assertNotNull(time)
        Assertions.assertEquals(BigInteger("1641593434076"), time!!.startTime)
        Assertions.assertEquals(BigInteger("1641593434076"), time!!.endTime)
        Assertions.assertEquals("test_user", time.user.username)
    }
}