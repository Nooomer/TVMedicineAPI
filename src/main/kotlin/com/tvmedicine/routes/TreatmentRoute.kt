package com.tvmedicine.routes

import com.tvmedicine.models.*
import com.tvmedicine.utils.dbUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException

@OptIn(ExperimentalSerializationApi::class)
fun Route.treatmentRouting() {
    var treatmentStorage = mutableListOf<TreatmentSModel>()
    var treatmentStorage_new = mutableListOf<FrontTreatment>()
    route("/treatment") {
        get {
            treatmentStorage = dbUtils.getAllTreatment()
            if (treatmentStorage.isNotEmpty()) {
                call.respond(treatmentStorage.toFront(treatmentStorage_new))
                treatmentStorage_new.clear()
                treatmentStorage.clear()
            } else {
               Responds.NotFoundError("treatment", call)
            }
        }
        get("{id?}") {

        }
        get("{treat_id?}/patient/{patient_id?}") {
            val patient_id = (call.parameters["patient_id"] ?: return@get call.respondText(
                "Missing patient_id",
                status = HttpStatusCode.BadRequest
            )).toInt()
            val treatment_id = (call.parameters["treat_id"] ?: return@get call.respondText(
                "Missing treat_id",
                status = HttpStatusCode.BadRequest
            )).toInt()
            treatmentStorage = dbUtils.getTreatmentByTreatIdAndPatientId(patient_id,treatment_id)
            treatmentOutput(treatmentStorage, treatmentStorage_new)
        }
        get("/patient/{patient_id?}") {
            val id = (call.parameters["patient_id"] ?: return@get call.respondText(
                "Missing patient_id",
                status = HttpStatusCode.BadRequest
            )).toInt()
            treatmentStorage = dbUtils.getTreatmentByPatientId(id)
            treatmentOutput(treatmentStorage, treatmentStorage_new)
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
private suspend fun PipelineContext<Unit, ApplicationCall>.treatmentOutput(
    treatmentStorage: MutableList<TreatmentSModel>,
    treatmentStorage_new: MutableList<FrontTreatment>
) {
    if (treatmentStorage.isNotEmpty()) {
        call.respond(treatmentStorage.toFront(treatmentStorage_new))
        treatmentStorage_new.clear()
        treatmentStorage.clear()
    } else {
        Responds.NotFoundError("treatment", call)
    }
}

fun MutableList<TreatmentSModel>.toFront(treatmentStorage_new: MutableList<FrontTreatment>): MutableList<FrontTreatment> {
    for(i in this.indices) {
        treatmentStorage_new.add(
            FrontTreatment(
                dbUtils.getPatientById(this[i].patient_id)[i].Surename,
                dbUtils.getPatientById(this[i].doctor_id)[i].Surename,
                this[i].start_date))
    }
    return treatmentStorage_new
}