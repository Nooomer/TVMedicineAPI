package com.tvmedicine.utils

import com.tvmedicine.models.PatientSModel
import com.tvmedicine.models.TreatmentSModel
import com.tvmedicine.models.patients
import com.tvmedicine.models.treatment
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

interface dbUtils {
    companion object {
        private val patientSModelStorage = mutableListOf<PatientSModel>()
        private val treatmentSModelStorage = mutableListOf<TreatmentSModel>()
        private fun addToStorage(storageType: String, patient: ResultRow, treatments: ResultRow = patient) {
            when (storageType) {
                "patientSModelStorage" -> {
                    patientSModelStorage.add(classField.newPatientsList(patients, patient))
                }
                "treatmentSModelStorage" -> {
                    treatmentSModelStorage.add(classField.newTreatmentList(treatment, treatments))
                }
            }
        }

        fun getAllPatients(): MutableList<PatientSModel> {
            transaction(Connection.TRANSACTION_SERIALIZABLE, 2) {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(patients)
                for (patient in patients.selectAll()) {
                    addToStorage("patientSModelStorage", patient)
                }
            }
            return patientSModelStorage
        }

        fun getPatientById(id: Int): MutableList<PatientSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(patients)
                for (patient in patients.select(patients.id eq id)) {
                    addToStorage("patientSModelStorage", patient)
                }
            }
            return patientSModelStorage
        }
        fun getPatientByLogin(login: String): MutableList<PatientSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(patients)
                for (patient in patients.select(patients.phone_number eq login)) {
                    addToStorage("patientSModelStorage", patient)
                }
            }
            return patientSModelStorage
        }

        fun getAllTreatment(): MutableList<TreatmentSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(treatment)
                for (treatments in treatment.selectAll()) {
                    addToStorage("treatmentSModelStorage", treatments)
                }
            }
            return treatmentSModelStorage
        }

        fun addNewPatient(patient: PatientSModel) {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(treatment)
                patients.insert {
                    it[surename] = patient.Surename
                    it[name] = patient.name
                    it[s_name] = patient.s_name
                    it[phone_number] = patient.phone_number
                    it[insurance_policy_number] = patient.insurance_policy_number
                    it[password] = patient.password
                }
            }
        }

        fun addNewTreatment(treatments: TreatmentSModel) {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(treatment)
                treatment.insert {
                    it[chat_id] = treatments.chat_id
                    it[patient_id] = treatments.patient_id
                    it[doctor_id] = treatments.doctor_id
                    it[start_date] = treatments.start_date
                    it[symptoms_id] = treatments.symptoms_id
                    it[sound_server_link_id] = treatments.sound_server_link_id
                    it[conclusion_id] = treatments.conclusion_id
                }
            }
        }
    }
}

