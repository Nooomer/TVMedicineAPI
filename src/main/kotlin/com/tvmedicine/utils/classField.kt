package com.tvmedicine.utils

import com.tvmedicine.models.PatientSModel
import com.tvmedicine.models.TreatmentSModel
import com.tvmedicine.models.patients
import com.tvmedicine.models.treatment
import org.jetbrains.exposed.sql.ResultRow

interface classField {
    companion object{
        fun newPatientsList(dbData: patients, patientObj: ResultRow): PatientSModel {
            return PatientSModel(
                patientObj[patients.id],
                patientObj[patients.surename],
                patientObj[patients.name],
                patientObj[patients.s_name],
                patientObj[patients.phone_number],
                patientObj[patients.insurance_policy_number],
                patientObj[patients.password]
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