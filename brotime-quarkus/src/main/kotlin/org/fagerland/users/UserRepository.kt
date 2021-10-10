package org.fagerland.users

import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository: PanacheRepository<User> {
}