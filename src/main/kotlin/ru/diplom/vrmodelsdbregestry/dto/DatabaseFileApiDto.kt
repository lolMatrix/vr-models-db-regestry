package ru.diplom.vrmodelsdbregestry.dto

import java.util.UUID

data class DatabaseFileApiDto(
    val id: UUID,
    val name: String,
    val description: String,
    val haveInLibrary: Boolean = false
)
