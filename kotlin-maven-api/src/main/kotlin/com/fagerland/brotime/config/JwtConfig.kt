package com.fagerland.brotime.config

import com.fagerland.brotime.filters.JwtFilter
import com.fagerland.brotime.services.JwtService
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtConfig(
    private val jwtService: JwtService
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(httpSecurity: HttpSecurity) {
        val filter = JwtFilter(jwtService)
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
