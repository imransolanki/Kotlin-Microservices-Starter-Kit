package org.edu.service

import arrow.core.Either
import org.edu.api.Pet
import org.edu.repository.PetRepository

class PetService(private val repository: PetRepository) {

    suspend fun createPet(pet: Pet): Either<Throwable, Pet> {
        val petId = repository.create(pet)
        return Either.Right(pet.copy(id = petId))
    }

    suspend fun updatePet(request: Pet): Either<Throwable, Pet> {
        val updated = repository.update(request)
        if (!updated) return Either.Left(Throwable(message = "Pet with id ${request.id} not found"))
        return Either.Right(request)
    }

    suspend fun getPet(petId: Long): Either<Throwable, Pet> {
        val result = repository.findById(petId)
            ?: return Either.Left(Throwable(message = "Pet with id $petId not found"))
        return Either.Right(result)
    }

    suspend fun deletePet(petName: String): Either<Throwable, Int> {
        val count = repository.deleteByName(petName)
        if (count == 0) return Either.Left(Throwable(message = "Pet with the name $petName not found"))
        return Either.Right(count)
    }
}
