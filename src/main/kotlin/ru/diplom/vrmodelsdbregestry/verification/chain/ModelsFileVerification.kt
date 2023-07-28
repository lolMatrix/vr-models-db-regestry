package ru.diplom.vrmodelsdbregestry.verification.chain

import org.springframework.stereotype.Component
import ru.diplom.vrmodelsdbregestry.verification.VerificationChain
import ru.diplom.vrmodelsdbregestry.verification.context.VerificationContext

@Component
class ModelsFileVerification : VerificationChain {
    override fun verify(context: VerificationContext) = context.apply {
        positioningInstruction!!.components.forEach { component ->
            check(filesList.any { fileName -> fileName.contains("${component.name}.obj") }) {
                "Компонент ${component.name}.obj не передан в контейнере"
            }
            check(filesList.any { fileName -> fileName.contains("${component.name}.mtl") }) {
                "Компонент ${component.name}.mtl не передан в контейнере"
            }
        }
    }
}