package com.tvmedicine.routes

import com.tvmedicine.DatabaseFactory.getDBConnection
import com.tvmedicine.models.Patientss
import com.tvmedicine.models.patients
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
    val patientssStorage = mutableListOf<Patientss>()
    route("/patient") {
        get {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(patients)
                for (patient in patients.selectAll()) {
                    patientssStorage.add(Patientss(patient[patients.id], patient[patients.surename]))
                }
            }
                if (patientssStorage.isNotEmpty()) {
                    call.respond(patientssStorage)
                    patientssStorage.clear()
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
                for (patient in patients.select(patients.id eq  id.toInt())) {
                    patientssStorage.add(Patientss(patient[patients.id], patient[patients.surename]))
                }
            }
            if (patientssStorage.isNotEmpty()) {
                call.respond(patientssStorage)
                patientssStorage.clear()
            } else {
                call.respondText("No patient found", status = HttpStatusCode.NotFound)
            }
        }
        post() {
            try {
                val patient = call.receive<Patientss>()
                transaction {
                    addLogger(StdOutSqlLogger)
                    SchemaUtils.create(patients)
                    patients.insert{ it[surename]= patient.Surename}
                }

                call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
            }
            catch (e: MissingFieldException)
            {
                call.respondText("Need more parameters", status = HttpStatusCode.BadRequest)
            }
        }
        delete("{id?}") {

        }
    }
}