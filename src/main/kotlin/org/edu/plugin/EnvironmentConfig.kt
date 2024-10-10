package org.edu.org.edu.plugin

data class EnvironmentConfig(
    val ktor: KtorConfig,
    val database: DatabaseConfig
)

data class KtorConfig(val application: ApplicationConfig, val deployment: DeploymentConfig)

data class DeploymentConfig(val port: String)

data class ApplicationConfig(val modules: List<String>)

data class ConnectionPoolConfig(val max: Int)
