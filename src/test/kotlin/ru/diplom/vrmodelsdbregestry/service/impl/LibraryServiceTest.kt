package ru.diplom.vrmodelsdbregestry.service.impl

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifyOrder
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import ru.diplom.vrmodelsdbregestry.model.Client
import ru.diplom.vrmodelsdbregestry.model.DatabaseFile
import ru.diplom.vrmodelsdbregestry.repository.UserRepository
import ru.diplom.vrmodelsdbregestry.service.ModelContainerService
import java.util.UUID
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class LibraryServiceTest(
    @MockK(relaxed = true) private val userRepository: UserRepository,
    @MockK(relaxUnitFun = true) private val modelContainerService: ModelContainerService,
) {
    @InjectMockKs
    private lateinit var libraryServiceImpl: LibraryServiceImpl

    @Test
    fun `should add to library model when model not contains in library`() {
        val model = mockk<DatabaseFile>()
        every { model.id } returns UUID.randomUUID()
        every { modelContainerService.getById(any()) } returns model
        val user = mockk<Client>()
        every { user.library } returns mutableListOf()
        every { userRepository.getReferenceById(any()) } returns user
        every { userRepository.save(any()) } returns mockk()

        libraryServiceImpl.addToLibrary(
            currentClient = Client(
                id = UUID.randomUUID(),
                name = "name",
                password = "password"
            ),
            fileId = UUID.randomUUID()
        )

        verifyOrder {
            userRepository.getReferenceById(any())
            user.library
            modelContainerService.getById(any())
            userRepository.save(any())
        }
    }

    @Test
    fun `should throw IllegalStateException when try to add model already exists in library`() {
        val model = mockk<DatabaseFile>()
        val modelId = UUID.randomUUID()
        every { model.id } returns modelId
        every { modelContainerService.getById(any()) } returns model
        val user = mockk<Client>()
        every { user.library } returns mutableListOf(model)
        every { userRepository.getReferenceById(any()) } returns user

        assertThrows<IllegalStateException> {
            libraryServiceImpl.addToLibrary(
                currentClient = Client(
                    id = UUID.randomUUID(),
                    name = "name",
                    password = "password"
                ),
                fileId = modelId
            )
        }.apply {
            assertThat(message, `is`("Данная модель уже есть в вашей библиотеке"))
        }
    }

    @Test
    fun `should return file bytes when model exists in library`() {
        val model = mockk<DatabaseFile>()
        val modelId = UUID.randomUUID()
        val expectedBytes = Random.nextBytes(10)
        every { model.id } returns modelId
        every { model.file } returns expectedBytes
        every { modelContainerService.getById(any()) } returns model
        val user = mockk<Client>()
        every { user.library } returns mutableListOf(model)
        every { user.id } returns UUID.randomUUID()
        every { userRepository.getReferenceById(any()) } returns user

        val bytes = libraryServiceImpl.getFileBytes(user, modelId)

        verifyOrder {
            assertThat(expectedBytes, `is`(bytes))
            userRepository.getReferenceById(any())
            user.library
            model.file
        }
    }

    @Test
    fun `should throw IllegalStateException when get file bytes when model not exist in library`() {
        val model = mockk<DatabaseFile>()
        every { model.id } returns UUID.randomUUID()
        every { modelContainerService.getById(any()) } returns model
        val user = mockk<Client>()
        every { user.library } returns mutableListOf(model)
        every { user.id } returns UUID.randomUUID()
        every { userRepository.getReferenceById(any()) } returns user

        assertThrows<IllegalStateException> {
            libraryServiceImpl.getFileBytes(user, UUID.randomUUID())
        }.apply {
            assertThat(message, `is`("Модель не найдена в вашей библиотеке"))
        }
    }

    @Test
    fun `should return library list`() {
        val model = mockk<DatabaseFile>()
        val user = mockk<Client>()
        every { user.library } returns mutableListOf(model)
        every { user.id } returns UUID.randomUUID()
        every { userRepository.getReferenceById(any()) } returns user

        val library = libraryServiceImpl.getList(user)

        verifyOrder {
            userRepository.getReferenceById(any())
            user.library
            assertThat(library, `is`(mutableListOf(model)))
        }
    }
}