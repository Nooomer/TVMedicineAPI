package com.tvmedicine

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import java.lang.System.getenv

object DatabaseFactory {
    var db: Database? = null
    fun init() {
        val ds = HikariDataSource(HikariConfig().apply {
            val appConfig = HoconApplicationConfig(ConfigFactory.load())
            jdbcUrl = appConfig.property("ktor.jdbc.url").getString()
            username = appConfig.property("ktor.jdbc.username").getString()
            password = appConfig.property("ktor.jdbc.password").getString()
            driverClassName = "org.postgresql.Driver"
            minimumIdle = 3
            maximumPoolSize = 5
        })
        db = Database.connect(ds)
    }
}