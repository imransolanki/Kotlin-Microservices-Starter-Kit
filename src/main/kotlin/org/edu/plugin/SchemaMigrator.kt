package org.edu.org.edu.plugin

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

class SchemaMigrator(private val datasource: DataSource) {
    init {
        Database.connect(datasource.hikari())
    }

    fun run() {
        val flyway = Flyway.configure().dataSource(datasource.hikari())
            .baselineOnMigrate(true)
            .load()

        try {
            flyway.info()
            flyway.migrate()
        } catch (e: Exception) {
            throw e
        }
    }
}
