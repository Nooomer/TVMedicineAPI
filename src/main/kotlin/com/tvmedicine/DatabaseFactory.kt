package com.tvmedicine

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.lang.System.getenv

object DatabaseFactory {
    var db: Database? = null
    fun init() {
        val ds = HikariDataSource(HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://ec2-34-246-86-78.eu-west-1.compute.amazonaws.com:5432/d9pi1oktfhqfs2"
            username = "gkexvzkwwcinvz"
            password = "64f0c5c9bba80ca4a3b74bf62ba0832f732ca357703ab00fe1182ccd03be6be6"
            driverClassName = "org.postgresql.Driver"
            minimumIdle = 3
            maximumPoolSize = 5
        })
        db = Database.connect(ds)
    }
}