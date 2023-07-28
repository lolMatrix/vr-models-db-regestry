package ru.diplom.vrmodelsdbregestry.verification.chain

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.util.ResourceUtils
import ru.diplom.vrmodelsdbregestry.utils.verificationContext

class MetaFileVerificationTest {

    private val metaFileVerification = MetaFileVerification()

    @Test
    fun `should do nothing when meta file exists`() {
        val verificationContext = verificationContext(
            bytes = ResourceUtils.getFile("classpath:valid_case.zip").readBytes(),
        )

        assertDoesNotThrow {
            metaFileVerification.verify(verificationContext)
            assertFalse(verificationContext.filesList.isEmpty())
            assertNotNull(verificationContext.positioningInstruction)
        }
    }

    @Test
    fun `should throw when file zip file is empty`() {
        val verificationContext = verificationContext(
            bytes = ResourceUtils.getFile("classpath:empty.zip").readBytes()
        )

        assertThrows<IllegalStateException> {
            metaFileVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Не представлен файл метаинформации"))
        }
    }
}