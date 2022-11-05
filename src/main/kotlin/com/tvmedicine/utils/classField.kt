package com.tvmedicine.utils

import com.tvmedicine.models.UsersSModel
import com.tvmedicine.models.TreatmentSModel
import com.tvmedicine.models.users
import com.tvmedicine.models.treatment
import org.jetbrains.exposed.sql.ResultRow

interface classField {
    companion object{
        fun newPatientsList(dbData: users, patientObj: ResultRow): UsersSModel {
            return UsersSModel(
                patientObj[users.id],
                patientObj[users.surename],
                patientObj[users.name],
                patientObj[users.s_name],
                patientObj[users.phone_number],
                patientObj[users.insurance_policy_number],
                patientObj[users.password],
                patientObj[users.user_type]
         )
        }
        fun newTreatmentList(dbData: treatment, treatmentObj: ResultRow): TreatmentSModel {
            return TreatmentSModel(
                treatmentObj[treatment.id],
                treatmentObj[treatment.chat_id],
                treatmentObj[treatment.patient_id],
                treatmentObj[treatment.doctor_id],
                treatmentObj[treatment.start_date],
                treatmentObj[treatment.symptoms_id],
                treatmentObj[treatment.sound_server_link_id],
                treatmentObj[treatment.conclusion_id]
            )
        }
    }
}