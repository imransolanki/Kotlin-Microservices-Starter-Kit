package org.edu.plugin

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
            gsonSettings()
        }
    }
}

fun GsonBuilder.gsonSettings(): GsonBuilder {
    setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    return this
}
