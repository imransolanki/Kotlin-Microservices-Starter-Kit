package org.edu.api

import arrow.core.Either
import io.konform.validation.Invalid
import io.konform.validation.Validation
import io.konform.validation.ValidationError
import io.konform.validation.jsonschema.*

fun validatePet(pet: Pet): Either<List<ValidationError>, Pet> {

    val result = PET_VALIDATOR.validate(pet)
    if (result is Invalid) return Either.Left(result.errors)
    return Either.Right(pet)
}

val PET_VALIDATOR = Validation<Pet> {

    Pet::name required {
        minLength(2) hint ("must be at least 2 character long")
        maxLength(50) hint ("must be at max 50 character long")
    }

    Pet::photoUrl required {
        maxLength(2048) hint ("must be at max 2048 character long")
        pattern(".*\\.(jpg|jpeg|png|gif)$".toRegex()) hint "must be a valid image URL"
    }

    Pet::status {
        addConstraint("must be one of [available, sold]") {
            it in listOf("available", "sold")
        }
    }
}
