package com.tvmedicine.utils

import com.tvmedicine.models.PatientSModel
import com.tvmedicine.models.patients
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

interface dbUtils {
    companion object {
        private val patientSModelStorage = mutableListOf<PatientSModel>()
        fun getAllPatients(): MutableList<PatientSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(patients)
                for (patient in patients.selectAll()) {
                    patientSModelStorage.add(
                        PatientSModel(
                            patient[patients.id],
                            patient[patients.surename],
                            patient[patients.name],
                            patient[patients.s_name],
                            patient[patients.phone_number],
                            patient[patients.insurance_policy_number],
                            patient[patients.password]
                        )
                    )
                }
            }
            return patientSModelStorage
        }
    }
}

