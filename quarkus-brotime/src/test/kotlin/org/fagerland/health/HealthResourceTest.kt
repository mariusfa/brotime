package org.fagerland.health

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

@QuarkusTest
class HealthResourceTest {

    @Test
    fun `should test health endpoint`() {
        given()
            .`when`().get("/health")
            .then()
                .statusCode(200)
                .body(CoreMatchers.`is`("healthy"))
    }
}