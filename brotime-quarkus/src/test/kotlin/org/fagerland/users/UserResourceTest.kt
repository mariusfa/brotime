package org.fagerland.users

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

@QuarkusTest
class UserResourceTest {

    @Test
    fun `should test hello user`() {
        RestAssured.given()
            .contentType("application/json")
            .body(
                """
                    {
                        "username": "test"
                    }
                """
            )
            .`when`().post("/api/users/hello")
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("hello test"))

    }

    @Test
    fun `should test register user`() {
        RestAssured.given()
            .contentType("application/json")
            .body(
                """
                    {
                        "username": "test"
                    }
                """.trimIndent()
            )
            .`when`().post("/api/users/register")
            .then()
            .statusCode(204)
    }
}