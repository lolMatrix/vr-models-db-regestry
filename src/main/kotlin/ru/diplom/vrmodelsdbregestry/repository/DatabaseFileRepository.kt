package ru.diplom.vrmodelsdbregestry.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import ru.diplom.vrmodelsdbregestry.model.DatabaseFile
import java.util.UUID

interface DatabaseFileRepository : JpaRepository<DatabaseFile, UUID>