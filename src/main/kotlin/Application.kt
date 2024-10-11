package org.edu

import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.edu.plugin.DataSource
import org.edu.plugin.SchemaMigrator
import org.edu.plugin.configureRouting
import org.edu.plugin.configureSerialization

fun main(args: Array<String>) {
    SchemaMigrator(DataSource(environment())).run()
    EngineMain.main(args)
}

fun Application.module() {
    configureApplication()
    configureRouting()
    configureSerialization()
}
