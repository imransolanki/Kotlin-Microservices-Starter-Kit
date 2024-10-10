package org.edu

import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.edu.org.edu.environment
import org.edu.org.edu.plugin.DataSource
import org.edu.org.edu.plugin.SchemaMigrator
import org.edu.org.edu.plugin.configureSerialization
import org.edu.plugin.configureRouting

fun main(args: Array<String>) {
    SchemaMigrator(DataSource(environment())).run()
    EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
    configureSerialization()
}
