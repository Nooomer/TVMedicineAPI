package com.tvmedicine.models
@kotlinx.serialization.Serializable
data class FrontTreatment(
    var id: Int,
    var patientSurename: String?,
    var doctorSurname: String?,
    val startdate: String?
)
