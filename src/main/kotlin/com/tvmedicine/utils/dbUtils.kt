package com.tvmedicine.utils

import com.tvmedicine.dbModels.*
import com.tvmedicine.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

interface dbUtils {
    companion object {
        private val usersSModelStorage = mutableListOf<UsersSModel>()
        private val treatmentSModelStorage = mutableListOf<TreatmentSModel>()
        private val doctorSModelStorage = mutableListOf<TreatmentSModel>()
        private val chatSModelStorage = mutableListOf<ChatSModel>()
        private val messagesSModelStorage = mutableListOf<MessagesSModel>()
        private fun addToStorage(storageType: String,resultRowsType: ResultRow) {
            when (storageType) {
                "patientSModelStorage" -> {
                    usersSModelStorage.add(classField.newPatientsList(users, resultRowsType))
                }
                "treatmentSModelStorage" -> {
                    treatmentSModelStorage.add(classField.newTreatmentList(treatment, resultRowsType))
                }
                "doctorSModelStorage" -> {
                    doctorSModelStorage.add(classField.newTreatmentList(treatment, resultRowsType))
                }
                "ChatSModelStorage" -> {
                chatSModelStorage.add(classField.newChatList(chats, resultRowsType))
                }
                "MessagesSModelStorage" -> {
                    messagesSModelStorage.add(classField.newMessagesList(messages, resultRowsType))
                }
            }
        }

        fun getAllPatients(): MutableList<UsersSModel> {
            transaction(Connection.TRANSACTION_SERIALIZABLE, 2) {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(users)
                for (patient in users.selectAll()) {
                    addToStorage("patientSModelStorage", patient)
                }
            }
            return usersSModelStorage
        }

        fun getPatientById(id: Int): MutableList<UsersSModel> {
            usersSModelStorage.clear()
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(users)
                for (patient in users.select(users.id eq id)) {
                    addToStorage("patientSModelStorage", patient)
                }
            }
            return usersSModelStorage
        }
        fun getPatientByLogin(login: String): MutableList<UsersSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(users)
                for (patient in users.select(users.phone_number eq login)) {
                    addToStorage("patientSModelStorage", patient)
                }
            }
            return usersSModelStorage
        }

        fun getAllTreatment(): MutableList<TreatmentSModel> {
            transaction(Connection.TRANSACTION_SERIALIZABLE, 2) {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(treatment)
                for (treatments in treatment.selectAll()) {
                    addToStorage("treatmentSModelStorage", treatments)
                }
            }
            return treatmentSModelStorage
        }

        fun getTreatmentById(id: Int): MutableList<TreatmentSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(treatment)
                for (treatments in treatment.select(treatment.id eq id)) {
                    addToStorage("treatmentSModelStorage", treatments)
                }
            }
            return treatmentSModelStorage
        }
        fun getChatById(id: Int): MutableList<ChatSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                //SchemaUtils.create(chats)
                for (Chats in chats.select(chats.id eq id)) {
                    addToStorage("ChatSModelStorage", Chats)
                }
            }
            return chatSModelStorage
        }
        fun getMessageByChat(id: Int): MutableList<MessagesSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                //SchemaUtils.create(treatment)
                for (messagess in messages.select(messages.id eq id)) {
                    addToStorage("treatmentSModelStorage", messagess)
                }
            }
            return messagesSModelStorage
        }
        fun getTreatmentByPatientId(patient_id: Int): MutableList<TreatmentSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(treatment)
                for (treatments in treatment.select(treatment.patient_id eq patient_id)) {
                    addToStorage("treatmentSModelStorage", treatments)
                }
            }
            return treatmentSModelStorage
        }
        fun getTreatmentByTreatIdAndPatientId(patient_id: Int, treat_id: Int): MutableList<TreatmentSModel> {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(treatment)
                for (treatments in treatment.select((treatment.patient_id eq patient_id) and (treatment.id eq treat_id))) {
                    addToStorage("treatmentSModelStorage", treatments)
                }
            }
            return treatmentSModelStorage
        }
        fun addNewPatient(patient: UsersSModel) {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(treatment)
                users.insert {
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

