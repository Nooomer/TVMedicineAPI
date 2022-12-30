package com.tvmedicine.dbModels

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object chats: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val treatmentId: Column<Int> = integer("treatment_id")
}