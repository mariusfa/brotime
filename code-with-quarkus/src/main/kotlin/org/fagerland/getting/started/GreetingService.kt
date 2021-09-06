package org.fagerland.getting.started

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GreetingService {

    fun greeting(name: String): String {
        return "Hello $name"
    }
}
