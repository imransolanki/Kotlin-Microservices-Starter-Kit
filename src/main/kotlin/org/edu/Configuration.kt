package org.edu.org.edu

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite
import com.sksamuel.hoplite.addResourceSource
import com.sksamuel.hoplite.sources.EnvironmentVariablesPropertySource
import org.edu.org.edu.plugin.EnvironmentConfig

@OptIn(ExperimentalHoplite::class)
fun environment(): EnvironmentConfig = ConfigLoaderBuilder.default().addPropertySource(
    EnvironmentVariablesPropertySource(
        useUnderscoresAsSeparator = true, allowUppercaseNames = true
    )
).addResourceSource("/application.yaml").withExplicitSealedTypes().build().loadConfigOrThrow<EnvironmentConfig>()
