package ru.diplom.vrmodelsdbregestry.service.impl

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import
import org.springframework.util.ResourceUtils
import ru.diplom.vrmodelsdbregestry.service.ContainerVerifier
import ru.diplom.vrmodelsdbregestry.verification.chain.FileNameVerification
import ru.diplom.vrmodelsdbregestry.verification.chain.MetaFileVerification
import ru.diplom.vrmodelsdbregestry.verification.chain.ModelsFileVerification

@SpringBootTest
class ContainerVerifierImplTest {

    @Autowired
    private lateinit var containerVerifier: ContainerVerifier

    @Test
    fun `should do nothing when container is valid`() {
        assertDoesNotThrow {
            containerVerifier.verifyContainer(
                fileName = "valid_case.zip",
                containerBytes = ResourceUtils.getFile("classpath:verifier-resource/valid_case.zip").readBytes()
            )
        }
    }

    @Test
    fun `should throw when container invalid - name`() {
        assertThrows<IllegalStateException> {
            containerVerifier.verifyContainer(
                fileName = "invalid.name.zip",
                containerBytes = ResourceUtils.getFile("classpath:verifier-resource/valid_case.zip").readBytes()
            )
        }.apply {
            assertThat(message, `is`("Недопустимое имя файла или имя не передано: invalid.name.zip"))
        }
    }

    @Test
    fun `should throw when container invalid - empty`() {
        assertThrows<IllegalStateException> {
            containerVerifier.verifyContainer(
                fileName = "empty.zip",
                containerBytes = ResourceUtils.getFile("classpath:verifier-resource/empty.zip").readBytes()
            )
        }.apply {
            assertThat(message, `is`("Не представлен файл метаинформации"))
        }
    }

    @Test
    fun `should throw when container invalid - without mtl`() {
        assertThrows<IllegalStateException> {
            containerVerifier.verifyContainer(
                fileName = "without_mtl.zip",
                containerBytes = ResourceUtils.getFile("classpath:verifier-resource/without_mtl.zip").readBytes()
            )
        }.apply {
            assertThat(message, `is`("Компонент bore_1.mtl не передан в контейнере"))
        }
    }

    @Test
    fun `should throw when container invalid - without obj`() {
        assertThrows<IllegalStateException> {
            containerVerifier.verifyContainer(
                fileName = "without_obj.zip",
                containerBytes = ResourceUtils.getFile("classpath:verifier-resource/without_obj.zip").readBytes()
            )
        }.apply {
            assertThat(message, `is`("Компонент bore_1.obj не передан в контейнере"))
        }
    }

    @Configuration
    @ComponentScan(
        useDefaultFilters = false,
        includeFilters = [
            ComponentScan.Filter(
                classes = [ContainerVerifier::class], type = FilterType.ASSIGNABLE_TYPE
            )
        ],
    )
    @Import(
        FileNameVerification::class,
        MetaFileVerification::class,
        ModelsFileVerification::class
    )
    class ContainerVerifierImplTestConfiguration
}