package com.tvmedicine.models

data class MessagesSModel(
    val id: Int,
    val message_text: String,
    val chat_id: Int,
    val from_id: Int,
    val to_id: Int,
    val send_time: String
)
