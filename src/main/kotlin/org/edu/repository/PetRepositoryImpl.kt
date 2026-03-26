package org.edu.repository

import org.edu.api.Pet
import org.edu.persistence.PetsEntity
import org.edu.persistence.PetsTable.name
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.OffsetDateTime
import java.time.ZoneOffset

class PetRepositoryImpl : PetRepository {
    override suspend fun create(pet: Pet): Long =
        newSuspendedTransaction {
            PetsEntity.new {
                name = pet.name
                status = pet.status
                photoUrl = pet.photoUrl
                createdAt = OffsetDateTime.now(ZoneOffset.UTC)
                updatedAt = OffsetDateTime.now(ZoneOffset.UTC)
            }.id.value
        }

    override suspend fun update(pet: Pet): Boolean =
        newSuspendedTransaction {
            PetsEntity.findByIdAndUpdate(pet.id!!) {
                it.name = pet.name
                it.status = pet.status
                it.updatedAt = OffsetDateTime.now(ZoneOffset.UTC)
            } != null
        }

    override suspend fun findById(petId: Long): Pet? =
        newSuspendedTransaction {
            PetsEntity.findById(petId)?.let {
                Pet(name = it.name, status = it.status, photoUrl = it.photoUrl, id = it.id.value)
            }
        }

    override suspend fun deleteByName(petName: String): Int =
        newSuspendedTransaction {
            PetsEntity.table.deleteWhere { name eq petName }
        }
}
