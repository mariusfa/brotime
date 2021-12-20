package org.fagerland.health

import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

@QuarkusTest
class HealthResourceTest {

    @Test
    fun `should test health endpoint`() {
        When {
            get("/health")
        } Then {
            statusCode(200)
            body(CoreMatchers.equalTo("healthy"))
        }
    }
}