package com.fagerland.brotime.config

import com.fagerland.brotime.filters.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SpringSecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean() : AuthenticationManager  {
       return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    protected override fun configure(httpSecurity: HttpSecurity) {
       httpSecurity.cors().and()
               .httpBasic().disable()
               .csrf().disable()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authorizeRequests()
               .anyRequest().authenticated()
               .and()
               .apply(JwtConfig())
    }

    @Throws(Exception::class)
    public override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/api/user/**")
    }

}