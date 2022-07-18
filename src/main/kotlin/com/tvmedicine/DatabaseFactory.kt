package com.tvmedicine

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*

object DatabaseFactory {
    var db: Database? = null
    fun init() {
        val ds = HikariDataSource(HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/TVMedicine"
            username = "postgres"
            password = "1234"
            driverClassName = "org.postgresql.Driver"
        })
         db = Database.connect(ds)
    }
    fun getDBConnection(): Database?{
        return db
    }
}