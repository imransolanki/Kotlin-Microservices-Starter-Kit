package org.edu.repository

import org.edu.api.Pet

interface PetRepository {
    suspend fun create(pet: Pet): Long

    suspend fun update(pet: Pet): Boolean

    suspend fun findById(petId: Long): Pet?

    suspend fun deleteByName(petName: String): Int
}
