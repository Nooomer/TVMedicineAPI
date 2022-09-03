package com.tvmedicine

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
import java.lang.System.getenv

object DatabaseFactory {
    var db: Database? = null
    fun init() {
        val ds = HikariDataSource(HikariConfig().apply {
            jdbcUrl = getenv("dbUrl")
            username = getenv("dbUsername")
            password = getenv("dbPasswords")
            driverClassName = getenv("driver")
        })
        db = Database.connect(ds)
    }

    fun getDBConnection(): Database? {
        return db
    }
}