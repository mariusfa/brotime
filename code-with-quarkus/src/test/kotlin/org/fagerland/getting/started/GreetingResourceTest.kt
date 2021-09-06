package org.fagerland.getting.started

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceTest {

    @Test
    fun testGreetingEndpoint() {
        given()
            .`when`().get("/greeting/marius")
            .then()
            .statusCode(200)
            .body(`is`("Hello marius"))
    }
}