package com.tvmedicine

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.lang.System.getenv

object DatabaseFactory {
    var db: Database? = null
    fun init() {
        val ds = HikariDataSource(HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://ec2-52-212-228-71.eu-west-1.compute.amazonaws.com:5432/dbdsigv57hq09n"
            username = "tvwsjjccvdnyfw"
            password = "a58a220cbbc3ba51316d626821391e28673f3b3582dac61b715716d0579542a9"
            driverClassName = "org.postgresql.Driver"
            minimumIdle = 3
            maximumPoolSize = 5
        })
        db = Database.connect(ds)
    }
}