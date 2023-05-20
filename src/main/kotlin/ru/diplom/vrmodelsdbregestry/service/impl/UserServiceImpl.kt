package ru.diplom.vrmodelsdbregestry.service.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.diplom.vrmodelsdbregestry.dto.LoginApiDto
import ru.diplom.vrmodelsdbregestry.model.Client
import ru.diplom.vrmodelsdbregestry.repository.UserRepository
import ru.diplom.vrmodelsdbregestry.service.UserService
import java.util.UUID

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) : UserService {

    override fun getUserByName(name: String) = userRepository.getByName(name).let {
        checkNotNull(it) {
            "Пользователь с таким именем не найден"
        }
    }

    override fun createUser(newClient: LoginApiDto) {
        check(isNotUniqUser(newClient.name)) {
            "Пользователь с таким именем уже существует"
        }
        userRepository.save(
            Client(
                id = UUID.randomUUID(),
                name = newClient.name,
                password = passwordEncoder.encode(newClient.password)
            )
        )
    }

    override fun loadUserByUsername(username: String) = getUserByName(username).let {
        org.springframework.security.core.userdetails.User(
            it.name,
            it.password,
            true,
            true,
            true,
            true,
            HashSet()
        )
    }

    private fun isNotUniqUser(userName: String) = !userRepository.existsByName(userName)
}