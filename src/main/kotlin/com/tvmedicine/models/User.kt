package com.tvmedicine.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var login: String,
    val password: String)
