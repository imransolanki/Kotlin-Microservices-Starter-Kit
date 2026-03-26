package org.edu.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.withTimeout
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun Route.healthRoute() {
    get("/health") {
        try {
            withTimeout(3000) { newSuspendedTransaction { exec("SELECT 1") } }
            call.respond(HttpStatusCode.OK, mapOf("status" to "UP"))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.ServiceUnavailable, mapOf("status" to "DOWN"))
        }
    }
}
