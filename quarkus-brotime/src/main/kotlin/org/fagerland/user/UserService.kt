package org.fagerland.user

import io.quarkus.elytron.security.common.BcryptUtil
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

@ApplicationScoped
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun registerUser(user: RegisterUserDTO) {
        userRepository.findByUsername(user.username)?.let {
            throw WebApplicationException("User already exists", Response.Status.CONFLICT)
        }

        val newUser = User()
        newUser.username = user.username
        newUser.hashedPassword = BcryptUtil.bcryptHash(user.password)
        newUser.role = "user"
        userRepository.persist(newUser)
    }
}