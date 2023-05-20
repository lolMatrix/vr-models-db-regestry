package ru.diplom.vrmodelsdbregestry.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.diplom.vrmodelsdbregestry.model.Client
import java.util.UUID

interface UserRepository : JpaRepository<Client, UUID> {
    fun getByName(name: String) : Client?
    fun existsByName(name: String) : Boolean
}