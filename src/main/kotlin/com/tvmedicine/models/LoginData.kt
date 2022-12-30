package com.tvmedicine.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(
    var login: String,
    val password: String)
