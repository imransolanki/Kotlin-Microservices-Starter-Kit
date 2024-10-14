package org.edu.api

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PetValidatorTest : StringSpec({

    "valid pet" {
        val validPet = Pet(id = 1, name = "Joe", photoUrl = "https://some-cdn.photo-url.png", status = "available")
        val actual = validatePet(validPet)

        actual.shouldBeRight()
        actual.getOrNull()!! shouldBe validPet
    }

    "invalid name" {
        val invalidPet = Pet(id = 10, name = "", photoUrl = "https://some-cdn.photo-url.png", status = "available")
        val actual = validatePet(invalidPet)

        actual.shouldBeLeft()
        actual.leftOrNull()!!.first().message shouldBe "must be at least 2 character long"
    }

    "invalid value of status" {
        val invalidPet =
            Pet(id = 10, name = "Woe", photoUrl = "https://some-cdn.photo-url.png", status = "not-available")
        val actual = validatePet(invalidPet)

        actual.shouldBeLeft()
        actual.leftOrNull()!!.first().message shouldBe "must be one of [available, sold]"
    }

})
