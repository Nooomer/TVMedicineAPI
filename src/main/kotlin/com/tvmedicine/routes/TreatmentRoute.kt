package com.tvmedicine.routes

import com.tvmedicine.models.*
import com.tvmedicine.utils.dbUtils
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

@OptIn(ExperimentalSerializationApi::class)
fun Route.treatmentRouting() {
    var treatmentStorage = mutableListOf<TreatmentSModel>()
    route("/treatment") {
        get {
            treatmentStorage = dbUtils.getAllTreatment()
            if (treatmentStorage.isNotEmpty()) {
                call.respond(treatmentStorage)
                treatmentStorage.clear()
            } else {
               Responds.NotFoundError("treatment", call)
            }
        }
        get("{id?}") {

        }
        post() {
            try {
                val treatments = call.receive<TreatmentSModel>()
                dbUtils.addNewTreatment(treatments)
                Responds.Created("Treatment", call)
            } catch (e: MissingFieldException) {
                Responds.NeedsParams("${e.missingFields}", call)
            }
        }
        delete("{id?}") {

        }
    }
}