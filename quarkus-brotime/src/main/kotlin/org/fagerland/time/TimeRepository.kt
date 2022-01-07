package org.fagerland.time

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TimeRepository : PanacheRepository<Time> {

}
