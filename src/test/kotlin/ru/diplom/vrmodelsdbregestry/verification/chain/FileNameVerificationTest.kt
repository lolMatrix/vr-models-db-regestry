package ru.diplom.vrmodelsdbregestry.verification.chain

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import ru.diplom.vrmodelsdbregestry.utils.verificationContext

class FileNameVerificationTest {

    private val fileNameVerification = FileNameVerification()

    @Test
    fun `should do nothing when filename is matches pattern`() {
        val verificationContext = verificationContext(
            fileName = "test-123_4.zip"
        )

        assertDoesNotThrow {
            fileNameVerification.verify(verificationContext)
        }
    }

    @Test
    fun `should throw when filename not matches pattern`() {
        val verificationContext = verificationContext(
            fileName = "test-123_4.rar"
        )

        assertThrows<IllegalStateException> {
            fileNameVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Недопустимое имя файла или имя не передано: ${verificationContext.fileName}"))
        }
    }

    @Test
    fun `should throw when filename is empty`() {
        val verificationContext = verificationContext(
            fileName = "",
        )

        assertThrows<IllegalStateException> {
            fileNameVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Недопустимое имя файла или имя не передано: ${verificationContext.fileName}"))
        }
    }
}