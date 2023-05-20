package ru.diplom.vrmodelsdbregestry.service

import org.springframework.security.core.userdetails.UserDetailsService
import ru.diplom.vrmodelsdbregestry.dto.LoginApiDto
import ru.diplom.vrmodelsdbregestry.model.Client

interface UserService : UserDetailsService {
    fun getUserByName(name: String): Client
    fun createUser(newClient: LoginApiDto)
}