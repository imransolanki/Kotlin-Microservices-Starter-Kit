package org.edu.plugin

import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import org.edu.api.healthRoute
import org.edu.api.routes

fun Application.configureRouting() {
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        healthRoute()
        routes()
    }
}
