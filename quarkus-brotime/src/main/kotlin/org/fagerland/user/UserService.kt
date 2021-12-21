package org.fagerland.user

import io.quarkus.elytron.security.common.BcryptUtil
import io.smallrye.jwt.build.Jwt
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

@ApplicationScoped
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun registerUser(user: UserDTO) {
        userRepository.findByUsername(user.username)?.let {
            throw WebApplicationException("User already exists", Response.Status.CONFLICT)
        }

        val newUser = User()
        newUser.username = user.username
        newUser.hashedPassword = BcryptUtil.bcryptHash(user.password)
        userRepository.persist(newUser)
    }

    fun loginUser(user: UserDTO): String {
        val userFound = userRepository.findByUsername(user.username)
            ?: throw WebApplicationException("Invalid user credentials", Response.Status.UNAUTHORIZED)

        if (!BcryptUtil.matches(user.password, userFound.hashedPassword)) {
            throw WebApplicationException("Invalid user credentials", Response.Status.UNAUTHORIZED)
        }

        return Jwt.upn(user.username).groups(setOf("user")).sign()
    }
}