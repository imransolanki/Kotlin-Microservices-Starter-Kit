package org.edu.plugin

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.edu.api.routes

fun Application.configureRouting() {
    routing { routes() }
}
