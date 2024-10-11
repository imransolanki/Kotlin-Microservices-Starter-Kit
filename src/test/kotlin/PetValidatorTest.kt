import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.edu.api.Pet
import org.edu.api.validatePet

class PetValidatorTest : StringSpec({

    "valid pet" {
        val validPet = Pet(id = 1, name = "Joe", photoUrl = "google-url", status = "available")
        val actual = validatePet(validPet)

        actual.shouldBeRight()
        actual.getOrNull()!! shouldBe validPet
    }

    "invalid id" {
        val invalidPet = Pet(id = 0, name = "Woe", photoUrl = "google-url", status = "available")
        val actual = validatePet(invalidPet)

        actual.shouldBeLeft()
        actual.leftOrNull()!!.first().message shouldBe "Minimum is 1"
    }

    "invalid name" {
        val invalidPet = Pet(id = 10, name = "", photoUrl = "google-url", status = "available")
        val actual = validatePet(invalidPet)

        actual.shouldBeLeft()
        actual.leftOrNull()!!.first().message shouldBe "must have at least 2 characters"
    }

    "invalid value of status" {
        val invalidPet =
            Pet(id = 10, name = "Woe", photoUrl = "google-url", status = "not-available")
        val actual = validatePet(invalidPet)

        actual.shouldBeLeft()
        actual.leftOrNull()!!.first().message shouldBe "must be one of [available, sold]"
    }

})
