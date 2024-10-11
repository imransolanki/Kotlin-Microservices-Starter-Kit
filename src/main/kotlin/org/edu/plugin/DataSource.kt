package org.edu.plugin

import com.zaxxer.hikari.HikariDataSource

class DataSource(private val configs: EnvironmentConfig) {
    fun hikari(): HikariDataSource {
        return configs.database.hikariDataSource()
    }
}
