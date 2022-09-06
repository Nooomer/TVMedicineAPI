package com.tvmedicine.utils

import com.tvmedicine.models.PatientSModel
import com.tvmedicine.models.TreatmentSModel
import com.tvmedicine.models.patients
import com.tvmedicine.models.treatment
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

interface dbUtils {
    companion object {
        private val patientSModelStorage = mutableListOf<PatientSModel>()
        private val treatmentSModelStorage = mutableListOf<TreatmentSModel>()
        fun getAllPatients(): MutableList<PatientSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(patients)
                for (patient in patients.selectAll()) {
                   patientSModelStorage.add(classField.newPatientsList(patients,patient))
                }
            }
            return patientSModelStorage
        }

        fun getAllTreatment(): MutableList<TreatmentSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(patients)
                for (patient in patients.selectAll()) {
                    patientSModelStorage.add(classField.newPatientsList(patients,patient))
                }
            }
            return treatmentSModelStorage
        }
    }
}

