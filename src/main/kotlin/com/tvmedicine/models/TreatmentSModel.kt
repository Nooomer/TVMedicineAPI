package com.tvmedicine.models

import kotlinx.serialization.Serializable
@Serializable
data class TreatmentSModel(
    val id: Int,
    val chat_id: Int,
    val patient_id: Int,
    val doctor_id: Int,
    val start_date: String,
    val symptoms_id: Int,
    val sound_server_link_id: String,
    val conclusion_id: Int
)
