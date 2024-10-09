package org.edu.org.edu.api

import arrow.core.Either
import io.konform.validation.Invalid
import io.konform.validation.Validation
import io.konform.validation.ValidationError
import io.konform.validation.jsonschema.*
import org.edu.api.Pet

fun validatePet(pet: Pet): Either<List<ValidationError>, Pet> {
    val result = PET_VALIDATOR.validate(pet)
    if (result is Invalid) return Either.Left(result.errors)
    return Either.Right(pet)
}

val PET_VALIDATOR = Validation<Pet> {

    Pet::id required {
        minimum(1) hint ("Minimum is 1")
        maximum(999) hint ("Maximum is 999")
    }

    Pet::name required {
        minLength(2)
        maxLength(50)
    }

    Pet::photoUrls ifPresent {
        minItems(0)
        maxItems(3)
    }

    Pet::status {
        addConstraint("must be one of [available, sold]") {
            it in listOf("available", "sold")
        }
    }
}
