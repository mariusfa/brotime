package com.fagerland.brotime.filters

import com.fagerland.brotime.repositories.UserRepository
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


class JwtFilter constructor(
    private val userRepository: UserRepository
) : GenericFilterBean() {
    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {
        val req: HttpServletRequest = servletRequest as HttpServletRequest
        val res: HttpServletResponse = servletResponse as HttpServletResponse
        val bearerToken = req.getHeader("Authentication")
        val username = getUsernameFromToken(bearerToken)
        if (username == null) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            return
        } else {
            val auth: Authentication = UsernamePasswordAuthenticationToken(username, null, arrayListOf())
            SecurityContextHolder.getContext().authentication = auth
            filterChain?.doFilter(servletRequest, servletResponse)
        }
    }

    private fun getUsernameFromToken(token: String?): String? {
        if (token != null) {
            val tokenValue = token.substring(7, token.length)
            try {
                val claims: Jws<Claims> = Jwts.parser().setSigningKey("secret").parseClaimsJws(tokenValue)
                if (!claims.body.expiration.before(Date())) {
                    val username: String = claims.body.subject
                    if (userRepository.findFirstByUsername(username) != null) {
                        return username
                    }
                }
            } catch (e: Exception) {
                return null
            }
        }
        return null
    }
}
