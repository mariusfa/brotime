package org.fagerland.user

import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.WebApplicationException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.Context
import javax.ws.rs.ext.Provider

@Provider
class UserFilter : ContainerRequestFilter {

    @Inject
    private lateinit var userRepository: UserRepository

    @Context
    private lateinit var resourceInfo: ResourceInfo

    override fun filter(requestContext: ContainerRequestContext) {
        resourceInfo.resourceMethod.getAnnotation(RolesAllowed::class.java)?.let {
            val username = requestContext.securityContext.userPrincipal.name
            userRepository.findByUsername(username) ?: throw WebApplicationException(401)
        }
    }
}