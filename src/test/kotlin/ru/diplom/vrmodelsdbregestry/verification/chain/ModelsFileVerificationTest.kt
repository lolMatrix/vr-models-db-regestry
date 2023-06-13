package ru.diplom.vrmodelsdbregestry.verification.chain

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.util.ResourceUtils
import ru.diplom.vrmodelsdbregestry.verification.context.VerificationContext

class ModelsFileVerificationTest {

    private val modelsFileVerification = ModelsFileVerification()

    @Test
    fun `should do nothing when all components in container`() {
        val verificationContext = VerificationContext(
            fileName = "valid_case.zip",
            bytes = ResourceUtils.getFile("classpath:valid_case.zip").readBytes()
        )

        assertDoesNotThrow {
            modelsFileVerification.verify(verificationContext)
        }
    }

    @Test
    fun `should throw when one of obj file not exits`() {
        val verificationContext = VerificationContext(
            fileName = "without_obj.zip",
            bytes = ResourceUtils.getFile("classpath:without_obj.zip").readBytes()
        )

        assertThrows<IllegalStateException> {
            modelsFileVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Компонент bore_1.obj не передан в контейнере"))
        }
    }

    @Test
    fun `should throw when one of mtl file not exits`() {
        val verificationContext = VerificationContext(
            fileName = "without_mtl.zip",
            bytes = ResourceUtils.getFile("classpath:without_mtl.zip").readBytes()
        )

        assertThrows<IllegalStateException> {
            modelsFileVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Компонент bore_1.mtl не передан в контейнере"))
        }
    }

    @Test
    fun `should throw when components files not exists`() {
        val verificationContext = VerificationContext(
            fileName = "without_components.zip",
            bytes = ResourceUtils.getFile("classpath:without_components.zip").readBytes()
        )

        assertThrows<IllegalStateException> {
            modelsFileVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Компонент bore_1.obj не передан в контейнере"))
        }
    }
}