package ru.diplom.vrmodelsdbregestry.service.impl

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder
import ru.diplom.vrmodelsdbregestry.dto.LoginApiDto
import ru.diplom.vrmodelsdbregestry.model.Client
import ru.diplom.vrmodelsdbregestry.repository.UserRepository
import java.util.UUID

@ExtendWith(MockKExtension::class)
class UserServiceTest(
    @RelaxedMockK private val passwordEncoder: PasswordEncoder,
    @MockK private val userRepository: UserRepository
) {
    @InjectMockKs
    private lateinit var userServiceImpl: UserServiceImpl

    @Test
    fun `should return user by name`() {
        val user = mockk<Client>()
        every { userRepository.getByName(any()) } returns user

        val actualUser = userServiceImpl.getUserByName("name")

        assertThat(user, `is`(actualUser))
        verify { userRepository.getByName("name") }
    }

    @Test
    fun `should create user`() {
        every { userRepository.existsByName(any()) } returns false
        every { passwordEncoder.encode(any()) } returns "passwordHash"
        every { userRepository.save(any()) } returns mockk()
        val user = LoginApiDto(
            name = "name",
            password = "password"
        )

        userServiceImpl.createUser(user)

        verifyOrder {
            userRepository.existsByName(user.name)
            passwordEncoder.encode(user.password)
            userRepository.save(any())
        }
    }

    @Test
    fun `should throw when try create not unique user`() {
        every { userRepository.existsByName(any()) } returns true
        val user = mockk<LoginApiDto>()
        every { user.name } returns "name"

        assertThrows<IllegalStateException> {
            userServiceImpl.createUser(user)
        }.apply {
            assertThat(message, `is`("Пользователь с таким именем уже существует"))
        }
    }
}