package com.tvmedicine.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.javatime.Date
@Serializable
data class TreatmentSModel(
    val id: Int,
    val chat_id: Int,
    val patient_id: Int,
    val doctor_id: Int,
    val start_date: String,
    val symptoms_id: Int,
    val sound_server_link_id: Int,
    val conclusion_id: Int
)
