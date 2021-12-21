package org.fagerland.user

import javax.annotation.security.RolesAllowed
import javax.enterprise.context.RequestScoped
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/api/users")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserResource(
    private val userService: UserService,
) {

    @POST
    @Path("/register")
    fun registerUser(user: UserDTO): Response {
        userService.registerUser(user)
        return Response.status(201).build()
    }

    @POST
    @Path("/login")
    fun loginUser(user: UserDTO): JwtDTO = JwtDTO(jwt = userService.loginUser(user))

    @GET
    @Path("/validate")
    @RolesAllowed("user")
    fun validateUser(): String {
        return "valid"
    }
}