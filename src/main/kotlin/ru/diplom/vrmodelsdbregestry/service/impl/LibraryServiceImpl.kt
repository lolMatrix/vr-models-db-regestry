package ru.diplom.vrmodelsdbregestry.service.impl

import org.springframework.stereotype.Service
import ru.diplom.vrmodelsdbregestry.model.Client
import ru.diplom.vrmodelsdbregestry.model.DatabaseFile
import ru.diplom.vrmodelsdbregestry.repository.UserRepository
import ru.diplom.vrmodelsdbregestry.service.LibraryService
import ru.diplom.vrmodelsdbregestry.service.ModelContainerService
import java.util.UUID

@Service
class LibraryServiceImpl(
    private val userRepository: UserRepository,
    private val modelContainerService: ModelContainerService
) : LibraryService {
    override fun addToLibrary(currentClient: Client, fileId: UUID) {
        userRepository.getReferenceById(currentClient.id)
            .apply {
                check(library.all { it.id != fileId }) {
                    "Данная модель уже есть в вашей библиотеке"
                }
            }
            .let {
                val file = modelContainerService.getById(fileId)
                it.library.add(file)
                userRepository.save(it)
            }
    }

    override fun getFileBytes(currentClient: Client, fileId: UUID) = userRepository.getReferenceById(currentClient.id)
        .library.firstOrNull { it.id == fileId }.let {
            checkNotNull(it) {
                "Модель не найдена в вашей библиотеке"
            }
        }.file

    override fun getList(currentClient: Client): List<DatabaseFile> {
        return userRepository.getReferenceById(currentClient.id).library
    }
}