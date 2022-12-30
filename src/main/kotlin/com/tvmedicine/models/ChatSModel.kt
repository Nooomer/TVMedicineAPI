package com.tvmedicine.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatSModel (
    val id: Int,
    val treatment_id: Int
)