package com.tvmedicine

import com.tvmedicine.models.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*
import java.sql.DriverManager

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.postgresql.Driver"

    }
}