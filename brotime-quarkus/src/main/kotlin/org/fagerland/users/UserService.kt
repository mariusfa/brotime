package org.fagerland.users

import org.fagerland.users.dto.RegisterUserReqDTO
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class UserService(
    val userRepository: UserRepository
) {
    @Transactional
    fun registerUser(registerUserReqDTO: RegisterUserReqDTO) {
        val user = registerUserDTOToUser(registerUserReqDTO)
        userRepository.persist(user)
    }

    private fun registerUserDTOToUser(registerUserReqDTO: RegisterUserReqDTO): User {
        val user = User()
        user.username = registerUserReqDTO.username
        return user
    }

    fun listUsers(): List<User> = userRepository.listAll()
}
