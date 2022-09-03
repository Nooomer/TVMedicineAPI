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
    val responds = Responds()

    route("/patient") {
        get {
            patientSModelStorage = dbUtils.getAllPatients()
            if (patientSModelStorage.isNotEmpty()) {
                call.respond(patientSModelStorage)
                patientSModelStorage.clear()
            } else {
                call.respondText("No patient found", status = HttpStatusCode.NotFound)
            }
        }
        get("{id?}") {
            val id = (call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            ))
            transaction {
                addLogger(StdOutSqlLogger)
                for (patient in patients.select(patients.id eq id.toInt())) {
                    patientSModelStorage.add(
                        PatientSModel(
                            patient[patients.id],
                            patient[patients.surename],
                            patient[patients.name],
                            patient[patients.s_name],
                            patient[patients.phone_number],
                            patient[patients.insurance_policy_number],
                            patient[patients.password]
                        )
                    )
                }
            }
            if (patientSModelStorage.isNotEmpty()) {
                call.respond(patientSModelStorage)
                patientSModelStorage.clear()
            } else {
                call.respondText("No patient found", status = HttpStatusCode.NotFound)
            }
        }
        post() {
            try {
                val patient = call.receive<PatientSModel>()
                transaction {
                    addLogger(StdOutSqlLogger)
                    SchemaUtils.create(patients)
                    patients.insert {
                        it[surename] = patient.Surename
                        it[name] = patient.name
                        it[s_name] = patient.s_name
                        it[phone_number] = patient.phone_number
                        it[insurance_policy_number] = patient.insurance_policy_number
                        it[password] = patient.password
                    }
                }
                responds.Created("Patient", call)
                //call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
            } catch (e: MissingFieldException) {
                responds.NeedsParams("${e.missingFields}", call)
                //call.respondText("Need more parameters", status = HttpStatusCode.BadRequest)
            }
        }
        delete("{id?}") {

        }
    }
}