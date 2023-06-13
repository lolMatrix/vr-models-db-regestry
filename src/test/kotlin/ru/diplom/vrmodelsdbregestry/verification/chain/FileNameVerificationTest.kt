package ru.diplom.vrmodelsdbregestry.verification.chain

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import ru.diplom.vrmodelsdbregestry.verification.context.VerificationContext

class FileNameVerificationTest {

    private val fileNameVerification = FileNameVerification()

    @Test
    fun `should do nothing when filename is matches pattern`() {
        val verificationContext = VerificationContext(
            fileName = "test-123_4.zip",
            bytes = byteArrayOf()
        )

        assertDoesNotThrow {
            fileNameVerification.verify(verificationContext)
        }
    }

    @Test
    fun `should throw when filename not matches pattern`() {
        val verificationContext = VerificationContext(
            fileName = "test-123_4.rar",
            bytes = byteArrayOf()
        )

        assertThrows<IllegalStateException> {
            fileNameVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Недопустимое имя файла или имя не передано: ${verificationContext.fileName}"))
        }
    }

    @Test
    fun `should throw when filename is empty`() {
        val verificationContext = VerificationContext(
            fileName = "",
            bytes = byteArrayOf()
        )

        assertThrows<IllegalStateException> {
            fileNameVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Недопустимое имя файла или имя не передано: ${verificationContext.fileName}"))
        }
    }
}