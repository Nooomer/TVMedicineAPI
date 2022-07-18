package com.tvmedicine.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object patients: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val surename: Column<String> = text("surename")
}