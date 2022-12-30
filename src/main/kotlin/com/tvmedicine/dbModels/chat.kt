package com.tvmedicine.dbModels

import com.tvmedicine.dbModels.users.autoIncrement
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object chat: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val treatmentId: Column<Int> = integer("treatment_id")
}