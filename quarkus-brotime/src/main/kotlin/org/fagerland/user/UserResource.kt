package org.fagerland.user

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserResource(
    private val userService: UserService
) {

    @POST
    @Path("/register")
    fun registerUser(user: RegisterUserDTO): Response {
        userService.registerUser(user)
        return Response.status(201).build()
    }
}