package ru.diplom.vrmodelsdbregestry.verification.chain

import org.springframework.stereotype.Component
import ru.diplom.vrmodelsdbregestry.verification.context.VerificationContext

@Component
class ModelsFileVerification : MetaFileVerification() {
    override fun verify(context: VerificationContext) {
        super.verify(context)

        context.positioningInstruction!!.components.forEach { component ->
            check(context.filesList.any { fileName -> fileName.contains("${component.name}.obj") }) {
                "Компонент ${component.name}.obj не передан в контейнере"
            }
            check(context.filesList.any { fileName -> fileName.contains("${component.name}.mtl") }) {
                "Компонент ${component.name}.mtl не передан в контейнере"
            }
        }
    }
}