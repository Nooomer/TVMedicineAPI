package com.tvmedicine.models
@kotlinx.serialization.Serializable
data class FrontTreatment(
    var patientSurename: String?,
    var doctorSurname: String?,
    val startdate: String?
)
