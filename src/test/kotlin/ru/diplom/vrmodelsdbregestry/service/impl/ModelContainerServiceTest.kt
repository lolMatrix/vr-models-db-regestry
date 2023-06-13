package ru.diplom.vrmodelsdbregestry.service.impl

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import ru.diplom.vrmodelsdbregestry.model.Client
import ru.diplom.vrmodelsdbregestry.model.DatabaseFile
import ru.diplom.vrmodelsdbregestry.repository.DatabaseFileRepository
import ru.diplom.vrmodelsdbregestry.repository.UserRepository
import ru.diplom.vrmodelsdbregestry.verification.VerificationChain
import java.util.UUID

@ExtendWith(MockKExtension::class)
class ModelContainerServiceTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val databaseFileRepository: DatabaseFileRepository,
    @MockK private val verificationChain: VerificationChain
) {
    @InjectMockKs
    private lateinit var modelContainerServiceImpl: ModelContainerServiceImpl

    @Test
    fun `should upload and add to library model`() {
        val user = client()
        every { userRepository.getReferenceById(any()) } returns user
        every { databaseFileRepository.save(any()) } returns mockk()
        every { userRepository.save(any()) } returns mockk()
        every { verificationChain.verify(any()) } returns Unit

        modelContainerServiceImpl.upload(
            name = "name",
            description = "desc",
            file = mockk(relaxed = true),
            createdBy = user
        )

        verifyOrder {
            assertThat(user.library.size, `is`(1))
            verificationChain.verify(any())
            databaseFileRepository.save(any())
            userRepository.getReferenceById(user.id)
            userRepository.save(any())
        }
    }

    @Test
    fun `should throw when verification failed`() {
        val user = client()
        every { verificationChain.verify(any()) } throws IllegalStateException()

        assertThrows<IllegalStateException> {
            modelContainerServiceImpl.upload(
                name = "name",
                description = "desc",
                file = mockk(relaxed = true),
                createdBy = user
            )
        }
        verifyOrder {
            verificationChain.verify(any())
        }
    }

    @Test
    fun `should get by id`() {
        val file = mockk<DatabaseFile>()
        val id = UUID.randomUUID()
        every { databaseFileRepository.getReferenceById(any()) } returns file

        val actualFile = modelContainerServiceImpl.getById(id)

        verify { databaseFileRepository.getReferenceById(id) }
        assertThat(file, `is`(actualFile))
    }

    @Test
    fun `should get page of files`() {
        val files = mockk<Page<DatabaseFile>>()
        every { databaseFileRepository.findAll(any<Pageable>()) } returns files

        val actualPage = modelContainerServiceImpl.getList(1, 1)

        verify { databaseFileRepository.findAll(PageRequest.of(1, 1, Sort.by("datetime").descending())) }
        assertThat(actualPage, `is`(files))
    }

    private fun client() = Client(
        id = UUID.randomUUID(),
        name = "name",
        password = "password"
    ).apply {
        library = mutableListOf()
    }
}