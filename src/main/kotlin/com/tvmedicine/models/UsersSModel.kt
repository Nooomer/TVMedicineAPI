package com.tvmedicine.models

import kotlinx.serialization.Serializable

@Serializable
data class UsersSModel(
    var id: Int,
    val Surename: String,
    val name: String,
    val s_name: String,
    val phone_number: String,
    val insurance_policy_number: String,
    val password: String,
    val user_type: String
    )
