package org.acme.rest.json

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test

@QuarkusTest
class FruitResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/fruits/hello")
          .then()
             .statusCode(200)
             .body(`is`("Hello RESTEasy"))
    }

    @Test
    fun `test fruit endpoint`() {
        given()
            .`when`().get("/fruits")
            .then()
            .statusCode(200)
            .body(containsString("apple"))
    }

}