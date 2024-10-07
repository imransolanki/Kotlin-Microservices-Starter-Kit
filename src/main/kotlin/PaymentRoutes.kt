package org.edu

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routes() {
    route("/") {
        get("") {
            call.respond(HttpStatusCode.OK, "Hello,World!")
        }
    }
}
