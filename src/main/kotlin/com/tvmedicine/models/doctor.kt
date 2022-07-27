package com.tvmedicine.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object doctor: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val surename: Column<String> = text("surename")
    val name: Column<String> = text("name")
    val s_name: Column<String> = text("s_name")
    val phone_number: Column<String> = text("phone_number")
    val password: Column<String> = text("insurance_policy_number")
    val hospital_id: Column<Int> = integer("hospital_id")
}