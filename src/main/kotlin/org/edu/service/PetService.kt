package org.edu.service

import org.edu.api.NotFoundException
import org.edu.api.Pet
import org.edu.repository.PetRepository
import org.slf4j.LoggerFactory

class PetService(private val repository: PetRepository) {
    private val log = LoggerFactory.getLogger(PetService::class.java)

    suspend fun createPet(pet: Pet): Pet {
        val petId = repository.create(pet)
        log.info("Created pet with id={} name={}", petId, pet.name)
        return pet.copy(id = petId)
    }

    suspend fun updatePet(request: Pet): Pet {
        val updated = repository.update(request)
        if (!updated) {
            log.warn("Pet not found for update id={}", request.id)
            throw NotFoundException("Pet with id ${request.id} not found")
        }
        log.info("Updated pet id={}", request.id)
        return request
    }

    suspend fun getPet(petId: Long): Pet {
        return repository.findById(petId)
            ?: run {
                log.warn("Pet not found id={}", petId)
                throw NotFoundException("Pet with id $petId not found")
            }
    }

    suspend fun deletePet(petName: String): Int {
        val count = repository.deleteByName(petName)
        if (count == 0) {
            log.warn("No pets found for deletion name={}", petName)
            throw NotFoundException("Pet with the name $petName not found")
        }
        log.info("Deleted {} pet(s) name={}", count, petName)
        return count
    }
}
