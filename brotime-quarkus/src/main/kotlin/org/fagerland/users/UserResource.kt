package org.fagerland.users

import org.fagerland.users.dto.RegisterUserReqDTO
import org.fagerland.users.dto.UserRequestDTO
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
    val userService: UserService
) {

    @POST
    @Path("/hello")
    fun helloUser(userResquestDTO: UserRequestDTO) = "hello ${userResquestDTO.username}"

    @POST
    @Path("/register")
    fun register(registerUserReqDTO: RegisterUserReqDTO): Response {
        try {
            userService.registerUser(registerUserReqDTO)
        } catch (e: Exception) {
            if (e.message?.contains("User conflict") == true) {
                return Response.status(409).build()
            }
            throw e
        }
        return Response.noContent().build()
    }
}