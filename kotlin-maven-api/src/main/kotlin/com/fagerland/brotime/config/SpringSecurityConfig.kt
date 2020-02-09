package com.fagerland.brotime.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class SpringSecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean() : AuthenticationManager  {
       return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    protected override fun configure(httpSecurity: HttpSecurity) {
       httpSecurity
               .httpBasic().disable()
               .csrf().disable()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authorizeRequests()
               .antMatchers("/api/user/**").permitAll()
               .antMatchers("healthy").permitAll()
               .and()
               .apply(JwtConfig())
    }

}