package com.fagerland.brotime.filters

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class JwtFilter : GenericFilterBean() {
    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {
        val req : HttpServletRequest = servletRequest as HttpServletRequest
        val bearerToken = req.getHeader("Authorization")
        if (bearerToken != null) {
            val token = bearerToken.substring(7, bearerToken.length)

            val claims: Jws<Claims> = Jwts.parser().setSigningKey("secret").parseClaimsJws(token)
            if (!claims.body.expiration.before(Date())) {
                val username: String = claims.body.subject
                val auth: Authentication = UsernamePasswordAuthenticationToken(username, null, arrayListOf())
                SecurityContextHolder.getContext().authentication = auth
            }
        }
        if (filterChain != null) {
            filterChain.doFilter(servletRequest, servletResponse)
        }
    }
}