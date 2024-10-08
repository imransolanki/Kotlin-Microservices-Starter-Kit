package org.edu.org.edu.api

import io.konform.validation.Validation
import io.konform.validation.jsonschema.*
import org.edu.api.Pet

val validatePet = Validation<Pet> {

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
