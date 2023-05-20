package ru.diplom.vrmodelsdbregestry.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.diplom.vrmodelsdbregestry.dto.DatabaseFileApiDto
import ru.diplom.vrmodelsdbregestry.model.Client
import ru.diplom.vrmodelsdbregestry.service.LibraryService
import java.util.UUID

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/lib")
class LibraryController(
    private val libraryService: LibraryService
) {

    @PostMapping("/{fileId}")
    @Operation(summary = "Добавить модель в библиотеку пользователя")
    fun add(@PathVariable fileId: UUID) {
        libraryService.addToLibrary(
            currentClient = SecurityContextHolder.getContext().authentication.principal as Client,
            fileId = fileId
        )
    }

    @GetMapping
    @Operation(summary = "Получить список всех моделей пользователя из библиотеки")
    fun getListForCurrentUser() =
        libraryService.getList(
            currentClient = SecurityContextHolder.getContext().authentication.principal as Client
        ).map {
            DatabaseFileApiDto(
                id = it.id,
                name = it.name,
                description = it.description
            )
        }

    @GetMapping("/{fileId}", produces = ["application/zip"])
    @Operation(summary = "Скачать модель с сервера из библиотеки пользователя")
    fun getFile(@PathVariable fileId: UUID): ByteArray {
        return libraryService.getFileBytes(
            currentClient = SecurityContextHolder.getContext().authentication.principal as Client,
            fileId = fileId
        )
    }
}