package org.edu.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

@Serializable
data class HealthResponse(val status: String)

fun Route.healthRoute() {
    get("/health") {
        try {
            withTimeout(3000) { newSuspendedTransaction { exec("SELECT 1") } }
            call.respond(HttpStatusCode.OK, HealthResponse("UP"))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.ServiceUnavailable, HealthResponse("DOWN"))
        }
    }
}
