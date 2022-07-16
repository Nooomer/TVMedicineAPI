package com.tvmedicine.models

import kotlinx.serialization.Serializable

@Serializable
data class Patient(var id: Int, val Surename: String)
