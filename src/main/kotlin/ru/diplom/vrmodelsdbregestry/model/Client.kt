package ru.diplom.vrmodelsdbregestry.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import java.util.UUID

@Entity
data class Client(
    @Id
    val id: UUID,
    val name: String,
    val password: String,
) {

    @ManyToMany
    @JoinTable(
        name = "database_file_user",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "database_file_id")]
    )
    lateinit var library: MutableList<DatabaseFile>
}