package org.edu

import io.ktor.server.application.*
import org.edu.plugin.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}
