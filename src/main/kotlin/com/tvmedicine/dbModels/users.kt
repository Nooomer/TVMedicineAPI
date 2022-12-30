package com.tvmedicine.dbModels

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object users: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val surename: Column<String> = text("surename")
    val name: Column<String> = text("name")
    val s_name: Column<String> = text("s_name")
    val phone_number: Column<String> = text("phone_number")
    val insurance_policy_number: Column<String> = text("insurance_policy_number")
    val password: Column<String> = text("password")
    val user_type: Column<String> = text("user_type")
}