package org.edu.plugin

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite
import com.sksamuel.hoplite.addResourceSource
import com.sksamuel.hoplite.sources.EnvironmentVariablesPropertySource
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureDependency() {
    install(Koin) {
        modules(appModules)
    }
}

@OptIn(ExperimentalHoplite::class)
fun environment(): EnvironmentConfig = ConfigLoaderBuilder.default().addPropertySource(
    EnvironmentVariablesPropertySource(
        useUnderscoresAsSeparator = true, allowUppercaseNames = true
    )
).addResourceSource("/application.yaml").withExplicitSealedTypes().build().loadConfigOrThrow<EnvironmentConfig>()
