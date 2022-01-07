package org.fagerland.time

import javax.annotation.security.RolesAllowed
import javax.enterprise.context.RequestScoped
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/api/time")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TimeResource(
    private val timeService: TimeService
) {

    @POST
    @RolesAllowed("user")
    fun create(timeDTO: TimeDTO, @Context ctx: SecurityContext): Response {
        timeService.create(timeDTO, ctx.userPrincipal.name)
        return Response.status(201).build()
    }

    @GET
    @RolesAllowed("user")
    fun list(): String {
        return "hello"
    }

    @PUT
    @RolesAllowed
    fun update(): String {
        return "hello"
    }

    @DELETE
    @RolesAllowed
    fun delete(): String {
        return "hello"
    }
}