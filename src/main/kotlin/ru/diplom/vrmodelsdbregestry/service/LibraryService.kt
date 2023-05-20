package ru.diplom.vrmodelsdbregestry.service

import ru.diplom.vrmodelsdbregestry.model.Client
import ru.diplom.vrmodelsdbregestry.model.DatabaseFile
import java.util.UUID

interface LibraryService {
    fun addToLibrary(currentClient: Client, fileId: UUID)
    fun getFileBytes(currentClient: Client, fileId: UUID): ByteArray
    fun getList(currentClient: Client) : List<DatabaseFile>
}