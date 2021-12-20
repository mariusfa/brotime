package org.fagerland.user

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository: PanacheRepository<User> {
    fun findByUsername(username: String) = find("username", username).firstResult()
}