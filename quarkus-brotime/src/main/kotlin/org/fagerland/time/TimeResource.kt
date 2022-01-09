package org.fagerland.time

import org.fagerland.time.dto.TimeRequestDTO
import org.fagerland.time.dto.TimeDTO
import javax.annotation.security.RolesAllowed
import javax.enterprise.context.RequestScoped
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
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
    fun create(timeRequestDTO: TimeRequestDTO, @Context ctx: SecurityContext): Response {
        timeService.create(timeRequestDTO, ctx.userPrincipal.name)
        return Response.status(Response.Status.CREATED).build()
    }

    @GET
    @RolesAllowed("user")
    fun list(@Context ctx: SecurityContext): List<TimeDTO> = timeService.list(ctx.userPrincipal.name).map { it.toDTO() }

    @PUT
    @Path("{id}")
    @RolesAllowed("user")
    fun update(timeRequestDTO: TimeRequestDTO, @Context ctx: SecurityContext, @PathParam("id") id: Long): Response {
        timeService.update(timeRequestDTO, ctx.userPrincipal.name, id)
        return Response.status(Response.Status.NO_CONTENT).build()
    }

    @DELETE
    @RolesAllowed("user")
    fun delete(): String {
        return "hello"
    }
}