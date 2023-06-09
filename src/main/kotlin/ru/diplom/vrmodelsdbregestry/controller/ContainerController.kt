package ru.diplom.vrmodelsdbregestry.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.Page
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.diplom.vrmodelsdbregestry.dto.DatabaseFileApiDto
import ru.diplom.vrmodelsdbregestry.model.Client
import ru.diplom.vrmodelsdbregestry.service.ModelContainerService
import ru.diplom.vrmodelsdbregestry.service.UserService

@RestController
@RequestMapping("/container")
@CrossOrigin(maxAge = 3600)
class ContainerController(
    private val modelContainerService: ModelContainerService,
    private val userService: UserService
) {

    @PostMapping("/{name}/{description}", consumes = ["multipart/form-data"])
    @Operation(summary = "Загрузить оптимизированную модель", security = [SecurityRequirement(name = "bearerAuth")])
    fun upload(
        @PathVariable name: String,
        @PathVariable description: String,
        @RequestBody container: MultipartFile
    ) {
        modelContainerService.upload(
            name = name,
            description = description,
            createdBy = SecurityContextHolder.getContext().authentication.principal as Client,
            file = container
        )
    }

    @GetMapping
    @Operation(summary = "Получить страницу списка всех моделей в базе данных")
    fun getList(
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ): Page<DatabaseFileApiDto> {
        val currentUserObject = SecurityContextHolder.getContext().authentication.principal
        if (currentUserObject is String) {
            return modelContainerService.getList(size, page).map {
                DatabaseFileApiDto(
                    id = it.id,
                    name = it.name,
                    description = it.description
                )
            }
        }
        val databaseUser = userService.getUserByName((currentUserObject as Client).name)
        return modelContainerService.getList(size, page).map {
            DatabaseFileApiDto(
                id = it.id,
                name = it.name,
                description = it.description,
                haveInLibrary = databaseUser.library.any { libFile -> libFile.id == it.id }
            )
        }
    }
}