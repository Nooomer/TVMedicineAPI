package com.tvmedicine.models

import kotlinx.serialization.Serializable

@Serializable
data class DoctorSModel(
    val id: Int,
    val surename: String,
    val name: String,
    val s_name: String,
    val phone_number: String,
    val password: String,
    val hospital_id: String
)
