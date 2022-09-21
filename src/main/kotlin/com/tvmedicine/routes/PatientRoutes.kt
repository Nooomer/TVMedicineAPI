package com.tvmedicine.routes

import com.tvmedicine.models.PatientSModel
import com.tvmedicine.models.Responds
import com.tvmedicine.models.patients
import com.tvmedicine.utils.dbUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

@OptIn(ExperimentalSerializationApi::class)
fun Route.patientRouting() {
    var patientSModelStorage = mutableListOf<PatientSModel>()

    route("/patient") {
        get {
            patientSModelStorage = dbUtils.getAllPatients()
            if (patientSModelStorage.isNotEmpty()) {
                call.respond(patientSModelStorage)
                patientSModelStorage.clear()
            } else {
                Responds.NotFoundError("patient",call)
            }
        }
        get("{id?}") {
            val id = (call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            ))
            patientSModelStorage = dbUtils.getPatientById(id.toInt())
            if (patientSModelStorage.isNotEmpty()) {
                call.respond(patientSModelStorage)
                patientSModelStorage.clear()
            } else {
                Responds.NotFoundError("patient",call)
            }
        }
        post() {
            try {
                val patient = call.receive<PatientSModel>()
                dbUtils.addNewPatient(patient)
                Responds.Created("Patient", call)
            } catch (e: MissingFieldException) {
                Responds.NeedsParams("${e.missingFields}", call)
            }
        }
        delete("{id?}") {

        }
    }
}