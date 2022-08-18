package com.tvmedicine.routes

import com.tvmedicine.models.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

@OptIn(ExperimentalSerializationApi::class)
fun Route.treatmentRouting() {
    val treatmentStorage = mutableListOf<TreatmentSModel>()
    val er = Responds()
    route("/treatment") {
        get {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(treatment)
                for (treatments in treatment.selectAll()) {
                    treatmentStorage.add(TreatmentSModel(
                        treatments[treatment.id],
                        treatments[treatment.chat_id],
                        treatments[treatment.patient_id],
                        treatments[treatment.doctor_id],
                        treatments[treatment.start_date],
                        treatments[treatment.symptoms_id],
                        treatments[treatment.sound_server_link_id],
                        treatments[treatment.conclusion_id]
                        ))
                }
            }
            if (treatmentStorage.isNotEmpty()) {
                call.respond(treatmentStorage)
                treatmentStorage.clear()
            } else {
                er.NotFoundError("treatment", call)
                //call.respondText("No patient found", status = HttpStatusCode.NotFound)

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