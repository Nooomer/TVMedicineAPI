package com.tvmedicine.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.Date
import org.jetbrains.exposed.sql.javatime.datetime

object treatment: Table() {
    val id = integer("id").autoIncrement()
    val chat_id = integer("chat_id")
    val patient_id  = integer("patient_id")
    val doctor_id = integer("doctor_id")
    val start_date = datetime("start_date")
    val symptoms_id = integer("symptoms_id")
    val sound_server_link_id = text("sound_server_link_id")
    val conclusion_id = integer("conclusion_id")

}