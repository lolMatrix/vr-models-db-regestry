package ru.diplom.vrmodelsdbregestry.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.diplom.vrmodelsdbregestry.dto.LoginApiDto
import ru.diplom.vrmodelsdbregestry.security.JwtUtils
import ru.diplom.vrmodelsdbregestry.service.UserService


@RestController
@RequestMapping("/auth")
@CrossOrigin(maxAge = 3600)
class AuthController(
    private val service: UserService,
    private val jwtUtils: JwtUtils,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/login", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Получить токин пользователя (Авторизация)")
    fun getAuthUser(@RequestBody client: LoginApiDto): String {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(client.name, client.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        return jwtUtils.generateJwtToken(authentication)
    }

    @PostMapping("/reg")
    @Operation(summary = "Зарегистрировать пользователя")
    fun register(@RequestBody newClient: LoginApiDto) = service.createUser(newClient)
}