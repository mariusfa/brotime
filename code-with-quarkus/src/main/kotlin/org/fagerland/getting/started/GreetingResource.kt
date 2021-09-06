package org.fagerland.getting.started

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
class GreetingResource(
    val greetingService: GreetingService
) {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    fun greeting(@PathParam("name") name: String): String {
        return greetingService.greeting(name)
    }
}