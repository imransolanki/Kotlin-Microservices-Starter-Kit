package org.edu.api

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.edu.service.PetService
import org.koin.ktor.ext.inject

fun Route.routes() {

    val service by inject<PetService>()

    route("/pet") {
        post("/") {
            val request = call.receive<Pet>()
            val validation = validatePet(request)
            validation.onLeft { return@post call.respond(BadRequest, ErrorResponse(it.joinToString { e -> e.message })) }
            call.respond(Created, service.createPet(request))
        }

        put("/") {
            val request = call.receive<Pet>()
            val validation = validatePet(request)
            validation.onLeft { throw BadRequestException(it.joinToString { e -> e.message }) }
            call.respond(OK, service.updatePet(request))
        }

        get("/{petId}") {
            val petId = call.parameters["petId"]?.toLongOrNull()
                ?: throw BadRequestException("petId must be a valid number")
            call.respond(OK, service.getPet(petId))
        }

        delete("/{petName}") {
            val petName = call.parameters["petName"]!!
            call.respond(OK, service.deletePet(petName))
        }
    }
}
