package ru.diplom.vrmodelsdbregestry.service

import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile
import ru.diplom.vrmodelsdbregestry.model.Client
import ru.diplom.vrmodelsdbregestry.model.DatabaseFile
import java.util.UUID

interface ModelContainerService {
    fun upload(
        name: String,
        description: String,
        createdBy: Client,
        file: MultipartFile
    )
    fun getById(fileId: UUID) : DatabaseFile
    fun getList(
        size: Int,
        page: Int
    ) : Page<DatabaseFile>
}