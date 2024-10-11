package org.edu

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite
import com.sksamuel.hoplite.addResourceSource
import com.sksamuel.hoplite.sources.EnvironmentVariablesPropertySource
import io.ktor.server.application.*
import org.edu.plugin.EnvironmentConfig
import org.edu.service.PetService
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureApplication() {
    val config = environment()
    install(Koin) {
        modules(environmentModule(config))
        modules(petService())
    }
}

fun environmentModule(config: EnvironmentConfig): Module {
    return module {
        single<EnvironmentConfig> { config }
    }
}

fun petService(): Module {
    return module {
        single { PetService() }
    }
}

@OptIn(ExperimentalHoplite::class)
fun environment(): EnvironmentConfig = ConfigLoaderBuilder.default().addPropertySource(
    EnvironmentVariablesPropertySource(
        useUnderscoresAsSeparator = true, allowUppercaseNames = true
    )
).addResourceSource("/application.yaml").withExplicitSealedTypes().build().loadConfigOrThrow<EnvironmentConfig>()
