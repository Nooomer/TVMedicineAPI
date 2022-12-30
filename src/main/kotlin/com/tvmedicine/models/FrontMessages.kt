package com.tvmedicine.models
@kotlinx.serialization.Serializable
data class FrontMessages(
    val fromId: Int,
    val toId: Int,
    val messageText: String,
    val sendTime: String
)
