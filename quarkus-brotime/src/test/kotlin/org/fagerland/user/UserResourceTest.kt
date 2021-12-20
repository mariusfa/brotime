package org.fagerland.user

import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test
import javax.ws.rs.core.MediaType

@QuarkusTest
class UserResourceTest {

    @Test
    fun `should test register user`() {

        Given {
            header("Content-Type", MediaType.APPLICATION_JSON)
            body(
                """
                {
                    "username": "test_user",
                    "password": "test_password
                }
            """.trimIndent()
            )
        } When {
            post("/api/users/register")
        } Then {
            statusCode(200)
        }
    }
}