package com.tvmedicine.routes

import com.tvmedicine.DatabaseFactory.getDBConnection
import com.tvmedicine.models.Patientss
import com.tvmedicine.models.patients
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

@OptIn(ExperimentalSerializationApi::class)
fun Route.patientRouting() {
    //val jdbcURL = "jdbc:postgresql://localhost:5432/TVMedicine"
    //val connections = DriverManager
        //.getConnection(jdbcURL, "postgres", "1234")
    val connections = getDBConnection()
    //val query = connections.prepareStatement("SELECT * FROM public.\"Patient\"")
    //val result = query.executeQuery()
    val patientssStorage = mutableListOf<Patientss>()
    route("/patient") {
        get {
            transaction {
                addLogger(StdOutSqlLogger)
                SchemaUtils.create(patients)

                for (patient in patients.selectAll()) {
                    patientssStorage.add(Patientss(patient[patients.id], patient[patients.surename]))
                }
            }
                if (patientssStorage.isNotEmpty()) {
                    call.respond(patientssStorage)
                    patientssStorage.clear()
                } else {
                    call.respondText("No patient found", status = HttpStatusCode.NotFound)
                }


        }
        get("{id?}") {
            val id = (call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            ))
            transaction {
                addLogger(StdOutSqlLogger)

                for (patient in patients.select(patients.id eq  id.toInt())) {
                    patientssStorage.add(Patientss(patient[patients.id], patient[patients.surename]))
                }
            }
            if (patientssStorage.isNotEmpty()) {
                call.respond(patientssStorage)
                patientssStorage.clear()
            } else {
                call.respondText("No patient found", status = HttpStatusCode.NotFound)
            }
            /*while(result.next()){

                // getting the value of the id column
                val ids = result.getInt("id")

                // getting the value of the name column
                val Surename = result.getString("Surename")

                /*
                constructing a User object and
                putting data into the list
                 */
                patientStorage.add(Patient(ids, Surename))
            }
            val patient =
                patientStorage.find { it.id == id.toInt() } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(patient)*/
        }
        post(/*"{surename?}"*/) {
            try {
                val patient = call.receive<Patientss>()
                transaction {
                    addLogger(StdOutSqlLogger)
                    SchemaUtils.create(patients)
                    patients.insert{ it[surename]= patient.Surename}
                }

                call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
            }
            catch (e: MissingFieldException)
            {
                call.respondText("Need more parameters", status = HttpStatusCode.BadRequest)
            }
            /*val surename = patient.Surename
            val query = connections.prepareStatement("INSERT INTO public.\"Patient\" (\"Surename\") VALUES ('$surename')")
            query.execute()
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)*/


        }
        delete("{id?}") {
            /*val id = (call.parameters["id"] ?: return@delete call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            ))
            var ids = id.toInt()
            val query = connections.prepareStatement("DELETE FROM public.\"Patient\" WHERE id=$ids")
            query.execute()
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)*/
        }
    }
}