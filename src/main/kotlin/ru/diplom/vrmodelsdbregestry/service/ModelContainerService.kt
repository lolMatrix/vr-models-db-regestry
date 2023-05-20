package ru.diplom.vrmodelsdbregestry.service

import org.springframework.data.domain.Page
import ru.diplom.vrmodelsdbregestry.model.DatabaseFile
import ru.diplom.vrmodelsdbregestry.model.Client
import java.util.UUID

interface ModelContainerService {
    fun upload(
        name: String,
        description: String,
        createdBy: Client,
        file: ByteArray
    )
    fun getById(fileId: UUID) : DatabaseFile
    fun getList(
        size: Int,
        page: Int
    ) : Page<DatabaseFile>
}