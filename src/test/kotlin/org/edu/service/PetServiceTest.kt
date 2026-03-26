package org.edu.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.edu.api.NotFoundException
import org.edu.api.Pet
import org.edu.persistence.PetsTable
import org.edu.repository.PetRepositoryImpl
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class PetServiceTest : FeatureSpec(
    {
        beforeAny {
            Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
            transaction { SchemaUtils.create(PetsTable) }
        }

        val service = PetService(PetRepositoryImpl())
        val pet =
            Pet(
                name = "Joe",
                photoUrl = "https://some-cdn.photo-url.png",
                status = "available",
                id = 1,
            )

        feature("createPet()") {
            scenario("valid pet") {
                val result = service.createPet(pet)
                result shouldBe pet
            }
        }

        feature("updatePet()") {
            scenario("update existing pet") {
                val newPet =
                    Pet(
                        name = "Joe",
                        photoUrl = "https://some-cdn.photo-url.png",
                        status = "available",
                        id = 1,
                    )
                val updatedPet = service.updatePet(newPet)
                updatedPet shouldBe newPet
            }
            scenario("update non existing pet") {
                val nonExistingPet =
                    Pet(
                        name = "Joe",
                        photoUrl = "https://some-cdn.photo-url.png",
                        status = "available",
                        id = 1000,
                    )
                val ex = shouldThrow<NotFoundException> { service.updatePet(nonExistingPet) }
                ex.message shouldBe "Pet with id ${nonExistingPet.id} not found"
            }
        }

        feature("getPet()") {
            scenario("find an existing pet") {
                service.getPet(1) shouldBe pet
            }

            scenario("find non-existing pet") {
                val petId: Long = 1000
                val ex = shouldThrow<NotFoundException> { service.getPet(petId) }
                ex.message shouldBe "Pet with id $petId not found"
            }
        }

        feature("deletePet") {
            service.createPet(pet)

            scenario("delete an existing pet") {
                service.deletePet(pet.name) shouldBeGreaterThan 0
            }

            scenario("delete a non-existing pet") {
                val petName = "absentPet"
                val ex = shouldThrow<NotFoundException> { service.deletePet(petName) }
                ex.message shouldBe "Pet with the name $petName not found"
            }
        }
    },
)
