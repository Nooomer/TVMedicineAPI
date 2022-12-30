package com.tvmedicine.dbModels

import com.tvmedicine.dbModels.users.autoIncrement
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object messages: Table() {
    val id: Column<Int> = integer("id")
    val message_text: Column<String> = text("message_text")
    val chat_id: Column<Int> = integer("chat_id")
    val fromId: Column<Int> = integer("from_id")
    val toId: Column<Int> = integer("to_id")
    val send_time: Column<String> = text("send_time")
}