package com.fagerland.brotime.config

import com.fagerland.brotime.filters.JwtFilter
import com.fagerland.brotime.repositories.UserRepository
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtConfig constructor(
    private val userRepository: UserRepository
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(httpSecurity: HttpSecurity) {
        val filter = JwtFilter(userRepository)
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
