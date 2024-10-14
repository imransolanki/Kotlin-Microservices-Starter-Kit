package org.edu.service

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import org.edu.api.Pet
import org.edu.persistence.PetsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class PetServiceTest : FeatureSpec(
    {
        beforeAny {
            Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
            transaction { SchemaUtils.create(PetsTable) }
        }

        val service = PetService()
        val pet = Pet(
            name = "Joe", photoUrl = "https://some-cdn.photo-url.png", status = "available",
            id = 1
        )

        feature("createPet()") {
            scenario("valid pet") {
                val result = service.createPet(pet)

                result.shouldBeRight()
                result.getOrNull()!! shouldBe pet
            }
        }

        feature("updatePet()") {
            scenario("update existing pet") {
                val newPet =
                    Pet(
                        name = "Joe", photoUrl = "https://some-cdn.photo-url.png", status = "available",
                        id = 1
                    )
                val updatedPet = service.updatePet(newPet)

                updatedPet.shouldBeRight()
                updatedPet.getOrNull()!! shouldBe newPet
            }
            scenario("update non existing pet") {
                val nonExistingPet =
                    Pet(
                        name = "Joe", photoUrl = "https://some-cdn.photo-url.png", status = "available",
                        id = 1000
                    )
                val result = service.updatePet(nonExistingPet)

                result.shouldBeLeft()
                result.leftOrNull()!!.message shouldBe "Pet with id ${nonExistingPet.id} not found"
            }
        }

        feature("getPet()") {
            scenario("find an existing pet") {
                val result = service.getPet(1)
                result.shouldBeRight()
            }

            scenario("find non-existing pet") {
                val petId: Long = 1000
                val result = service.getPet(petId)
                result.shouldBeLeft()
                result.leftOrNull()!!.message shouldBe "Pet with id $petId not found"
            }
        }

        feature("deletePet") {
            service.createPet(pet)

            scenario("delete an existing pet") {
                val actual = service.deletePet(pet.name)
                actual.shouldBeRight()
            }

            scenario("delete a non-existing pet") {
                val petName = "absentPet"
                val result = service.deletePet(petName)
                result.shouldBeLeft()
                result.leftOrNull()!!.message shouldBe "Pet with the name $petName not found"
            }
        }
    }
)

