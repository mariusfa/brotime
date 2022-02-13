package org.fagerland.user

import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.smallrye.jwt.build.Jwt
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.core.MediaType

@QuarkusTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserResourceTest {

    @Inject
    private lateinit var userRepository: UserRepository

    private val username = "test_user"
    private val password = "test_password"

    @AfterAll
    @Transactional
    fun tearDown() {
        userRepository.deleteAll()
    }

    @Test
    @Order(1)
    fun `should test register user`() {
        Given {
            header("Content-Type", MediaType.APPLICATION_JSON)
            body(
                """
                {
                    "username": "$username",
                    "password": "$password"
                }
            """.trimIndent()
            )
        } When {
            post("/api/users/register")
        } Then {
            statusCode(201)
        }

        val savedUser = userRepository.findByUsername(username)
        Assertions.assertNotNull(savedUser)
        Assertions.assertEquals(1, userRepository.count())
    }

    @Test
    @Order(2)
    fun `should test user login`() {
        Given {
            header("Content-Type", MediaType.APPLICATION_JSON)
            body(
                """
                {
                    "username": "$username",
                    "password": "$password"
                }
            """.trimIndent()
            )
        } When {
            post("/api/users/login")
        } Then {
            statusCode(200)
            body("jwt", CoreMatchers.notNullValue())
        }

        Assertions.assertEquals(1, userRepository.count())
    }

    @Test
    @Transactional
    fun `should test validate user`() {
        val token = Jwt.upn(username).groups(setOf("user")).sign()

        Given {
            header("Content-Type", MediaType.APPLICATION_JSON)
            header("Authorization", "Bearer $token")
        } When {
            get("/api/users/validate")
        } Then {
            statusCode(200)
            body(CoreMatchers.equalTo("valid"))
        }
    }

    @Test
    @Transactional
    fun `should not validate fake token`() {
        val token = "not valid token"

        Given {
            header("Content-Type", MediaType.APPLICATION_JSON)
            header("Authorization", "Bearer $token")
        } When {
            get("/api/users/validate")
        } Then {
            statusCode(401)
        }
    }

    @Test
    @Transactional
    fun `should not validate user not registered`() {
        val token = Jwt.upn("test_user_not_registered").groups(setOf("user")).sign()

        Given {
            header("Content-Type", MediaType.APPLICATION_JSON)
            header("Authorization", "Bearer $token")
        } When {
            get("/api/users/validate")
        } Then {
            statusCode(401)
        }
    }

    @Test
    @Transactional
    fun `should not validate when missing header`() {
        Given {
            header("Content-Type", MediaType.APPLICATION_JSON)
        } When {
            get("/api/users/validate")
        } Then {
            statusCode(401)
        }
    }
}