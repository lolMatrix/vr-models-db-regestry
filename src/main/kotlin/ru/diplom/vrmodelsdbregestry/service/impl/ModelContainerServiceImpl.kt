package ru.diplom.vrmodelsdbregestry.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.diplom.vrmodelsdbregestry.model.Client
import ru.diplom.vrmodelsdbregestry.model.DatabaseFile
import ru.diplom.vrmodelsdbregestry.repository.DatabaseFileRepository
import ru.diplom.vrmodelsdbregestry.repository.UserRepository
import ru.diplom.vrmodelsdbregestry.service.ContainerVerifier
import ru.diplom.vrmodelsdbregestry.service.ModelContainerService
import java.time.OffsetDateTime
import java.util.UUID

@Service
class ModelContainerServiceImpl(
    private val databaseFileRepository: DatabaseFileRepository,
    private val userRepository: UserRepository,
    private val containerVerifier: ContainerVerifier
) : ModelContainerService {

    override fun upload(
        name: String,
        description: String,
        createdBy: Client,
        file: MultipartFile
    ) {
        containerVerifier.verifyContainer(
            fileName = file.originalFilename.orEmpty(),
            containerBytes = file.bytes
        )
        val databaseFile = DatabaseFile(
            id = UUID.randomUUID(),
            name = name,
            description = description,
            createdByUserId = createdBy.id,
            file = file.bytes,
            datetime = OffsetDateTime.now()
        )
        databaseFileRepository.save(databaseFile)

        userRepository.getReferenceById(createdBy.id).apply {
            library.add(databaseFile)
            userRepository.save(this)
        }
    }

    override fun getById(fileId: UUID) = databaseFileRepository.getReferenceById(fileId)

    override fun getList(size: Int, page: Int) =
        databaseFileRepository.findAll(
            PageRequest.of(page, size, Sort.by("datetime").descending())
        )
}