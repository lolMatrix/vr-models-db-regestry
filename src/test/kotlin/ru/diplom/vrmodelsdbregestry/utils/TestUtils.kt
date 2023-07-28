package ru.diplom.vrmodelsdbregestry.utils

import ru.diplom.vrmodelsdbregestry.verification.context.VerificationContext
import ru.diplom.vrmodelsdbregestry.verification.dto.Component
import ru.diplom.vrmodelsdbregestry.verification.dto.PositioningInstruction

fun verificationContext(
    fileName: String = "filename.zip",
    bytes: ByteArray = byteArrayOf(),
    filesList: MutableList<String> = mutableListOf(),
    positioningInstruction: PositioningInstruction? = null
) = VerificationContext(
    fileName = fileName,
    bytes = bytes,
    filesList = filesList
).apply {
    this.positioningInstruction = positioningInstruction
}

fun positionInstruction(
    components: List<Component> = listOf(component())
) = PositioningInstruction(
    components = components
)

fun component(
    name: String = "name"
) = Component(
    name = name
)