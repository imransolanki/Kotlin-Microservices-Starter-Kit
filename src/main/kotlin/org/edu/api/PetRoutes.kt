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
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("org.edu.api.PetRoutes")

fun Route.routes() {

    val service by inject<PetService>()

    route("/pet") {
        post("/") {
            val request = call.receive<Pet>()
            val validation = validatePet(request)
            validation.onLeft {
                log.warn("Validation failed: {}", it.joinToString { e -> e.message })
                return@post call.respond(BadRequest, ErrorResponse(it.joinToString { e -> e.message }))
            }
            val pet = service.createPet(request)
            log.info("POST /pet/ -> 201 id={}", pet.id)
            call.respond(Created, pet)
        }

        put("/") {
            val request = call.receive<Pet>()
            val validation = validatePet(request)
            validation.onLeft { throw BadRequestException(it.joinToString { e -> e.message }) }
            val pet = service.updatePet(request)
            log.info("PUT /pet/ -> 200 id={}", pet.id)
            call.respond(OK, pet)
        }

        get("/{petId}") {
            val petId = call.parameters["petId"]?.toLongOrNull()
                ?: throw BadRequestException("petId must be a valid number")
            val pet = service.getPet(petId)
            log.info("GET /pet/{} -> 200", petId)
            call.respond(OK, pet)
        }

        delete("/{petName}") {
            val petName = call.parameters["petName"]!!
            val count = service.deletePet(petName)
            log.info("DELETE /pet/{} -> 200 deleted={}", petName, count)
            call.respond(OK, count)
        }
    }
}
