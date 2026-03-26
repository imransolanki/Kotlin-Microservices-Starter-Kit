package org.edu.service

import org.edu.api.NotFoundException
import org.edu.api.Pet
import org.edu.repository.PetRepository

class PetService(private val repository: PetRepository) {

    suspend fun createPet(pet: Pet): Pet {
        val petId = repository.create(pet)
        return pet.copy(id = petId)
    }

    suspend fun updatePet(request: Pet): Pet {
        val updated = repository.update(request)
        if (!updated) throw NotFoundException("Pet with id ${request.id} not found")
        return request
    }

    suspend fun getPet(petId: Long): Pet {
        return repository.findById(petId)
            ?: throw NotFoundException("Pet with id $petId not found")
    }

    suspend fun deletePet(petName: String): Int {
        val count = repository.deleteByName(petName)
        if (count == 0) throw NotFoundException("Pet with the name $petName not found")
        return count
    }
}
