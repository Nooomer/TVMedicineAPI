package com.tvmedicine.routes

import com.tvmedicine.models.PatientSModel
import com.tvmedicine.models.patients
import com.tvmedicine.models.treatment
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

@OptIn(ExperimentalSerializationApi::class)
fun Route.TreatmentRouting() {
    val treatmentStorage = mutableListOf<PatientSModel>()
    route("/treatment") {
        get {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(treatment)
                for (doctor in treatment.selectAll()) {
                    treatmentStorage.add(PatientSModel(patient[patients.id], patient[patients.surename]))
                }
            }
            if (treatmentStorage.isNotEmpty()) {
                call.respond(treatmentStorage)
                treatmentStorage.clear()
            } else {
                call.respondText("No patient found", status = HttpStatusCode.NotFound)
            }
        }
        get("{id?}") {

        }
        post() {

        }
        delete("{id?}") {

        }
    }
}