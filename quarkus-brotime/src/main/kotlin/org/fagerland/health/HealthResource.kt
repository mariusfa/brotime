package org.fagerland.health

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/health")
class HealthResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun health() = "healthy"
}