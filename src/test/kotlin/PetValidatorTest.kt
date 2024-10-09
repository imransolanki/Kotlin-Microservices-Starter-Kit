import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.edu.api.Pet
import org.edu.org.edu.api.validatePet

class PetValidatorTest : StringSpec({

    "valid pet" {
        val validPet = Pet(id = 1, name = "Joe", photoUrls = emptyList(), status = "available")
        val actual = validatePet(validPet)

        actual.shouldBeRight()
        actual.getOrNull()!! shouldBe validPet
    }

    "invalid id" {
        val invalidPet = Pet(id = 0, name = "Woe", photoUrls = emptyList(), status = "available")
        val actual = validatePet(invalidPet)

        actual.shouldBeLeft()
        actual.leftOrNull()!!.first().message shouldBe "Minimum is 1"
    }

    "invalid name" {
        val invalidPet = Pet(id = 10, name = "", photoUrls = emptyList(), status = "available")
        val actual = validatePet(invalidPet)

        actual.shouldBeLeft()
        actual.leftOrNull()!!.first().message shouldBe "must have at least 2 characters"
    }

    "invalid number of photo urls" {
        val invalidPet =
            Pet(id = 10, name = "Woe", photoUrls = listOf("string", "string", "string", "string"), status = "Available")
        val actual = validatePet(invalidPet)

        actual.shouldBeLeft()
        actual.leftOrNull()!!.first().message shouldBe "must have at most 3 items"
    }

    "invalid value of status" {
        val invalidPet =
            Pet(id = 10, name = "Woe", photoUrls = emptyList(), status = "not-available")
        val actual = validatePet(invalidPet)

        actual.shouldBeLeft()
        actual.leftOrNull()!!.first().message shouldBe "must be one of [available, sold]"
    }

})
