package org.fagerland.users

import org.fagerland.users.dto.UserRequestDTO
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserResource {

    @POST
    @Path("/hello")
    fun helloUser(userResquestDTO: UserRequestDTO) = "hello ${userResquestDTO.username}"
}