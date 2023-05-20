package ru.diplom.vrmodelsdbregestry.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.OffsetDateTime
import java.util.UUID

@Entity
data class DatabaseFile(
    @Id
    val id: UUID,
    val name: String,
    val description: String,
    val createdByUserId: UUID,
    val file: ByteArray,
    val datetime: OffsetDateTime
) {
    @ManyToOne
    @JoinColumn(name = "createdByUserId", insertable = false, updatable = false)
    lateinit var createdBy: Client

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DatabaseFile

        if (name != other.name) return false
        if (description != other.description) return false
        if (createdBy != other.createdBy) return false
        if (!file.contentEquals(other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + createdBy.hashCode()
        result = 31 * result + file.contentHashCode()
        return result
    }
}
