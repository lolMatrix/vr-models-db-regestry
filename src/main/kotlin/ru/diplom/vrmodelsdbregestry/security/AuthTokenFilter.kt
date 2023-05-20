package ru.diplom.vrmodelsdbregestry.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import ru.diplom.vrmodelsdbregestry.service.UserService

class AuthTokenFilter(
    private val jwtUtils: JwtUtils,
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt: String? = parseJwt(request)
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                val username: String = jwtUtils.getUserNameFromJwtToken(jwt)
                val userDetails = userService.getUserByName(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, arrayListOf()
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            logger.error("Cannot set user authentication: {}", e)
        }

        filterChain.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        if (request.getHeaders("Authorization").toList().isEmpty()) return null
        val headerAuth = request.getHeader("Authorization")
        return if (headerAuth.isNotBlank() && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7, headerAuth.length)
        } else null
    }
}