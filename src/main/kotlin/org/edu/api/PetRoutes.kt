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

            validation.isLeft { return@post call.respond(BadRequest, validation.leftOrNull()!!) }

            val result = service.createPet(request)
            return@post call.respond(Created, result.getOrNull()!!)
        }

        put("/") {
            val request = call.receive<Pet>()

            val validation = validatePet(request)

            validation.isLeft { return@put call.respond(BadRequest, validation.leftOrNull()!!) }

            val result = service.updatePet(request)
            result.isLeft { return@put call.respond(BadRequest, result.leftOrNull()!!) }
            result.isRight { return@put call.respond(OK, result.getOrNull()!!) }
        }

        get("/{petId}") {
            val petId: Long = call.parameters["petId"]!!.toLong()

            val result = service.getPet(petId)
            result.isLeft { return@get call.respond(BadRequest, result.leftOrNull()!!) }
            result.isRight { return@get call.respond(OK, result.getOrNull()!!) }
        }

        delete("/{petName}") {
            val petName: String = call.parameters["petName"]!!.toString()

            val result = service.deletePet(petName)
            result.isLeft { return@delete call.respond(BadRequest, result.leftOrNull()!!) }
            result.isRight { return@delete call.respond(OK, result.getOrNull()!!) }
        }
    }
}
