package org.edu.plugin

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

data class DatabaseConfig(
    val name: String, val user: String, val password: String, val hostname: String, val port: Int,
    val driver: String, val autoCommit: Boolean, val pool: ConnectionPoolConfig
) {
    fun hikariDataSource(): HikariDataSource {
        return HikariDataSource(hikariConfig())
    }

    private fun url(): String = "jdbc:postgresql://${hostname}:${port}/${name}?user=${user}&password=${password}"

    private fun hikariConfig(): HikariConfig = HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url()
        maximumPoolSize = pool.max
        isAutoCommit = autoCommit
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }
}
