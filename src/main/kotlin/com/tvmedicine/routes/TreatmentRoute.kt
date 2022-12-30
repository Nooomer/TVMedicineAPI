package com.tvmedicine.routes

import com.tvmedicine.dbModels.treatment
import com.tvmedicine.models.*
import com.tvmedicine.utils.Responds
import com.tvmedicine.utils.Utils
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
    var ChatStorage = mutableListOf<ChatSModel>()
    var MessagesStorage = mutableListOf<MessagesSModel>()
    var treatmentStorage_new = mutableListOf<FrontTreatment>()
    var MessagesStorage_new = mutableListOf<FrontMessages>()
    route("/treatment") {
        get {
            treatmentStorage = dbUtils.getAllTreatment()
            if (treatmentStorage.isNotEmpty()) {
                call.respond(treatmentStorage.toFront(treatmentStorage_new, MessagesStorage_new))
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
            treatmentStorage = dbUtils.getTreatmentByTreatIdAndPatientId(patient_id, treatment_id)
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
        get("{treat_id}/chat") {
            val treat_id = (call.parameters["treat_id"] ?: return@get call.respondText(
                "Missing treatment id",
                status = HttpStatusCode.BadRequest
            )).toInt()
                treatmentStorage = dbUtils.getTreatmentById(treat_id)
                ChatStorage = dbUtils.getChatById(treatmentStorage[0].chat_id)
                MessagesStorage = dbUtils.getMessageByChat(ChatStorage[0].id)
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
    treatmentStorage_new: MutableList<FrontTreatment>,
) {
    if (treatmentStorage.isNotEmpty()) {
        call.respond(treatmentStorage.toFront(treatmentStorage_new, MessagesStorage_new = mutableListOf()))
        treatmentStorage_new.clear()
        treatmentStorage.clear()
    } else {
        Responds.NotFoundError("treatment", call)
    }
}
private suspend fun PipelineContext<Unit, ApplicationCall>.messagesOutput(
    messagesStorage: MutableList<MessagesSModel>,
    messagesStorage_new: MutableList<FrontMessages>,
) {
    if (messagesStorage.isNotEmpty()) {
        call.respond(messagesStorage.toFront(treatmentStorage_new = mutableListOf(), messagesStorage_new))
        messagesStorage_new.clear()
        messagesStorage.clear()
    } else {
        Responds.NotFoundError("treatment", call)
    }
}

inline fun <reified T>MutableList<T>.toFront(treatmentStorage_new: MutableList<FrontTreatment>, MessagesStorage_new: MutableList<FrontMessages>): Any {
    return when(T::class){
        TreatmentSModel::class ->{
            for (i in this.indices) {
                treatmentStorage_new.add(
                    FrontTreatment(
                        (this[i] as TreatmentSModel).id,
                        dbUtils.getPatientById((this[i] as TreatmentSModel).patient_id)[0].Surename,
                        dbUtils.getPatientById((this[i] as TreatmentSModel).doctor_id)[0].Surename,
                        (this[i] as TreatmentSModel).start_date
                    )
                )
            }
            treatmentStorage_new
        }
        MessagesSModel::class ->{
            for (i in this.indices) {
                MessagesStorage_new.add(
                    FrontMessages(
                        (this[i] as MessagesSModel).from_id,
                        (this[i] as MessagesSModel).to_id,
                        (this[i] as MessagesSModel).message_text,
                        (this[i] as MessagesSModel).send_time
                    )
                )
            }
            MessagesStorage_new
        }
        else -> throw IllegalStateException("Unknown Generic Type")
    }
}
