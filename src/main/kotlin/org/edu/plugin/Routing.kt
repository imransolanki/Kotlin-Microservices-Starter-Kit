package org.edu.plugin

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.edu.routes

fun Application.configureRouting() {
    routing { routes() }
}
