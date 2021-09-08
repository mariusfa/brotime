package org.acme.rest.json

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/fruits")
class FruitResource {

    @GET
    fun list(): List<Fruit> {
        return listOf(Fruit("apple", "apple descrption"), Fruit("Banana", "banana description"))
    }


    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello RESTEasy"
}