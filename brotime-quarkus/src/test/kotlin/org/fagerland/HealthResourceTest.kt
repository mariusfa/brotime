package org.fagerland

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

@QuarkusTest
class HealthResourceTest {

    @Test
    fun `test health endpoint`() {
        RestAssured.given()
            .`when`().get("/api/health")
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("Healthy"))
    }
}