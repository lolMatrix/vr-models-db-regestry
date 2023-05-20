package ru.diplom.vrmodelsdbregestry.repository

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import ru.diplom.vrmodelsdbregestry.model.Client
import java.util.UUID

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    classes = [
        UserRepositoryJpaTest.UserRepositoryJpaTestConfig::class
    ]
)
class UserRepositoryJpaTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `should find user by name`() {
        val client = Client(
            id = UUID.randomUUID(),
            name = "name",
            password = "password"
        )
        userRepository.save(client)

        val foundClient = userRepository.getByName("name")

        assertNotNull(foundClient)
        foundClient!!
        assertThat(client.id, `is`(foundClient.id))
        assertThat(client.name, `is`(foundClient.name))
        assertThat(client.password, `is`(foundClient.password))
    }

    @Test
    fun `should return null when user not founded by name`() {
        val foundClient = userRepository.getByName("name")

        assertNull(foundClient)
    }

    @TestConfiguration
    @EnableJpaRepositories(basePackageClasses = [UserRepository::class])
    class UserRepositoryJpaTestConfig
}