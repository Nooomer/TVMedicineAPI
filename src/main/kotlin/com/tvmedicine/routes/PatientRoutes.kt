package com.tvmedicine.routes

import com.tvmedicine.models.UsersSModel
import com.tvmedicine.models.Responds
import com.tvmedicine.utils.dbUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException

@OptIn(ExperimentalSerializationApi::class)
fun Route.patientRouting() {
    var usersSModelStorage = mutableListOf<UsersSModel>()

    route("/patient") {
        get {
            usersSModelStorage = dbUtils.getAllPatients()
            if (usersSModelStorage.isNotEmpty()) {
                call.respond(usersSModelStorage)
                usersSModelStorage.clear()
            } else {
                Responds.NotFoundError("patient",call)
            }
        }
        get("{id?}") {
            val id = (call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            ))
            usersSModelStorage = dbUtils.getPatientById(id.toInt())
            if (usersSModelStorage.isNotEmpty()) {
                call.respond(usersSModelStorage)
                usersSModelStorage.clear()
            } else {
                Responds.NotFoundError("patient",call)
            }
        }
        post() {
            try {
                val patient = call.receive<UsersSModel>()
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