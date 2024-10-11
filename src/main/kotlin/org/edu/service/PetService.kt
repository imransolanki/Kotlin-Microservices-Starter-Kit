package org.edu.service

import arrow.core.Either
import org.edu.api.Pet
import org.edu.persistence.PetsEntity
import org.edu.persistence.PetsTable.name
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.OffsetDateTime
import java.time.ZoneOffset

class PetService {

    fun createPet(pet: Pet): Either<Throwable, Pet> {
        val petId = transaction {
            PetsEntity.new {
                name = pet.name
                status = pet.status
                photoUrl = pet.photoUrl
                createdAt = OffsetDateTime.now(ZoneOffset.UTC)
                updatedAt = OffsetDateTime.now(ZoneOffset.UTC)
            }.id.value
        }
        return Either.Right(pet.copy(id = petId))
    }

    fun updatePet(request: Pet): Either<Throwable, Pet> {
        val result = transaction {
            PetsEntity.findByIdAndUpdate(
                request.id!!
            ) {
                it.name = request.name
                it.status = request.status
                it.updatedAt = OffsetDateTime.now(ZoneOffset.UTC)
            }
        }
        if (result == null) return Either.Left(Throwable(message = "Pet with id ${request.id} not found"))
        return Either.Right(request)
    }

    fun getPet(petId: Long): Either<Throwable, Pet> {
        val result = transaction { PetsEntity.findById(petId) }

        if (result == null) return Either.Left(Throwable(message = "Pet with id $petId not found"))
        return Either.Right(
            Pet(
                name = result.name,
                status = result.status,
                photoUrl = result.photoUrl,
                id = result.id.value
            )
        )
    }

    fun deletePet(petName: String): Either<Throwable, Int> {
        val count = transaction { PetsEntity.table.deleteWhere { name eq petName } }

        if (count == 0) return Either.Left(Throwable(message = "Pet with the name $petName not found"))
        return Either.Right(count)
    }
}
