package org.edu.api

import io.konform.validation.Invalid
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.edu.org.edu.api.validatePet

fun Route.routes() {

    route("/pet") {
        post("/") {
            val request = call.receive<Pet>()

            val validation = validatePet(request)

            if (validation is Invalid) {
                return@post call.respondText {
                    BadRequest
                    validation.errors.toString()
                }
            }

            return@post call.respondText {
                Created
                "${request.name} is Created"
            }
        }

        put("/") {
            val request = call.receive<Pet>()

            val validation = validatePet(request)

            if (validation is Invalid) {
                return@put call.respondText {
                    BadRequest
                    validation.errors.toString()
                }
            }

            return@put call.respondText {
                OK
                "${request.name} is Updated"
            }
        }

        get("/{petId}") {
            return@get call.respond(
                OK,
                Pet(id = 1, name = "Woogie", photoUrls = listOf("google-url", "insta-url"), status = "available")
            )
        }

        delete("/{petId}") {
            return@delete call.respond(NoContent)
        }
    }
}
