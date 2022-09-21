package com.tvmedicine

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.lang.System.getenv

object DatabaseFactory {
    var db: Database? = null
    fun init() {
        val ds = HikariDataSource(HikariConfig().apply {
            jdbcUrl = getenv("dbUrl")
            username = getenv("dbUsername")
            password = getenv("dbPasswords")
            driverClassName = getenv("driver")
            minimumIdle = 3
            maximumPoolSize = 5
        })
        db = Database.connect(ds)
    }
}