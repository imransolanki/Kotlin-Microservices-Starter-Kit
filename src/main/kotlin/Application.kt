package org.edu

import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.edu.plugin.*

fun main(args: Array<String>) {
    SchemaMigrator(DataSource(environment())).run()
    EngineMain.main(args)
}

fun Application.module() {
    configureDependency()
    configureRouting()
    configureSerialization()
}
