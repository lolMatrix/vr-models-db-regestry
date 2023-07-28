package ru.diplom.vrmodelsdbregestry.verification.chain

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import ru.diplom.vrmodelsdbregestry.utils.component
import ru.diplom.vrmodelsdbregestry.utils.positionInstruction
import ru.diplom.vrmodelsdbregestry.utils.verificationContext

class ModelsFileVerificationTest {

    private val modelsFileVerification = ModelsFileVerification()

    @Test
    fun `should do nothing when all components in container`() {
        val verificationContext = verificationContext(
            filesList = mutableListOf("bore_1.mtl", "bore_1.obj"),
            positioningInstruction = positionInstruction(
                components = listOf(
                    component("bore_1")
                )
            )
        )

        assertDoesNotThrow {
            modelsFileVerification.verify(verificationContext)
        }
    }

    @Test
    fun `should throw when one of obj file not exits`() {
        val verificationContext = verificationContext(
            filesList = mutableListOf("bore_1.mtl"),
            positioningInstruction = positionInstruction(
                components = listOf(
                    component("bore_1")
                )
            )
        )

        assertThrows<IllegalStateException> {
            modelsFileVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Компонент bore_1.obj не передан в контейнере"))
        }
    }

    @Test
    fun `should throw when one of mtl file not exits`() {
        val verificationContext = verificationContext(
            filesList = mutableListOf("bore_1.obj"),
            positioningInstruction = positionInstruction(
                components = listOf(
                    component("bore_1")
                )
            )
        )

        assertThrows<IllegalStateException> {
            modelsFileVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Компонент bore_1.mtl не передан в контейнере"))
        }
    }

    @Test
    fun `should throw when components files not exists`() {
        val verificationContext = verificationContext(
            positioningInstruction = positionInstruction(
                components = listOf(
                    component("bore_1")
                )
            )
        )

        assertThrows<IllegalStateException> {
            modelsFileVerification.verify(verificationContext)
        }.apply {
            assertThat(message, `is`("Компонент bore_1.obj не передан в контейнере"))
        }
    }
}