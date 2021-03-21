package com.fagerland.brotime.filters

import com.fagerland.brotime.repositories.UserRepository
import com.fagerland.brotime.services.JwtService
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
import javax.servlet.http.HttpServletResponse


class JwtFilter(
    private val jwtService: JwtService
) : GenericFilterBean() {
    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {
        val req: HttpServletRequest = servletRequest as HttpServletRequest
        val res: HttpServletResponse = servletResponse as HttpServletResponse
        val validUsername = jwtService.getUsernameFromRequest(req)
        if (validUsername != null) {
            val auth: Authentication = UsernamePasswordAuthenticationToken(validUsername, null, arrayListOf())
            SecurityContextHolder.getContext().authentication = auth
            filterChain?.doFilter(servletRequest, servletResponse)
        } else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            return
        }
    }
}
