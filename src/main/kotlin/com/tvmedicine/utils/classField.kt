package com.tvmedicine.utils

import com.tvmedicine.models.*
import com.tvmedicine.dbModels.*
import org.jetbrains.exposed.sql.ResultRow

interface classField {
    companion object{
        fun newPatientsList(dbData: users, patientObj: ResultRow): UsersSModel {
            return UsersSModel(
                patientObj[dbData.id],
                patientObj[dbData.surename],
                patientObj[dbData.name],
                patientObj[dbData.s_name],
                patientObj[dbData.phone_number],
                patientObj[dbData.insurance_policy_number],
                patientObj[dbData.password],
                patientObj[dbData.user_type]
         )
        }
        fun newTreatmentList(dbData: treatment, treatmentObj: ResultRow): TreatmentSModel {
            return TreatmentSModel(
                treatmentObj[dbData.id],
                treatmentObj[dbData.chat_id],
                treatmentObj[dbData.patient_id],
                treatmentObj[dbData.doctor_id],
                treatmentObj[dbData.start_date],
                treatmentObj[dbData.symptoms_id],
                treatmentObj[dbData.sound_server_link_id],
                treatmentObj[dbData.conclusion_id]
            )
        }
        fun newChatList(dbData: chats, chatObj: ResultRow): ChatSModel {
            return ChatSModel(
                chatObj[dbData.id],
                chatObj[dbData.treatmentId]
            )
        }
        fun newMessagesList(dbData: messages, chatObj: ResultRow): MessagesSModel {
            return MessagesSModel(
                chatObj[dbData.id],
                chatObj[dbData.message_text],
                chatObj[dbData.chat_id],
                chatObj[dbData.fromId],
                chatObj[dbData.toId],
                chatObj[dbData.send_time],
            )
        }
    }
}