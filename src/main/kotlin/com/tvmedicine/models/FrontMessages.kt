package com.tvmedicine.models

data class FrontMessages(
    val fromId: Int,
    val toId: Int,
    val messageText: String,
    val sendTime: String
)
