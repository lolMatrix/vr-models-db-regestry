package ru.diplom.vrmodelsdbregestry.verification.context

import ru.diplom.vrmodelsdbregestry.verification.dto.PositioningInstruction

data class VerificationContext(
    val fileName: String,
    val bytes: ByteArray,
    val filesList: MutableList<String> = mutableListOf()
) {
    var positioningInstruction: PositioningInstruction? = null
}
