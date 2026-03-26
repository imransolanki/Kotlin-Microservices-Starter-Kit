package org.edu.plugin

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.edu.api.BadRequestException
import org.edu.api.ErrorResponse
import org.edu.api.NotFoundException
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("org.edu.plugin.StatusPages")

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<NotFoundException> { call, cause ->
            logger.warn("404: {}", cause.message)
            call.respond(HttpStatusCode.NotFound, ErrorResponse(cause.message ?: "Not found"))
        }
        exception<BadRequestException> { call, cause ->
            logger.warn("400: {}", cause.message)
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message ?: "Bad request"))
        }
        exception<Exception> { call, cause ->
            logger.error("500: {}", cause.message, cause)
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse(cause.message ?: "Internal server error"))
        }
    }
}
