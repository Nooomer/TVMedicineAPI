package com.tvmedicine.routes

import com.tvmedicine.DatabaseFactory
import com.tvmedicine.models.Patient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.DriverManager

fun Route.patientRouting() {
    val jdbcURL = "jdbc:postgresql://localhost:5432/TVMedicine"
    val connections = DriverManager
        .getConnection(jdbcURL, "postgres", "1234")
    val query = connections.prepareStatement("SELECT * FROM public.\"Patient\"")
    val result = query.executeQuery()
    val patientStorage = mutableListOf<Patient>()
    route("/patient") {
        get {
            while(result.next()){

                // getting the value of the id column
                val id = result.getInt("id")

                // getting the value of the name column
                val Surename = result.getString("Surename")

                /*
                constructing a User object and
                putting data into the list
                 */
                patientStorage.add(Patient(id, Surename))
            }
            if (patientStorage.isNotEmpty()) {
                call.respond(patientStorage)
            } else {
                call.respondText("No patient found", status = HttpStatusCode.NotFound)
            }

        }
        get("{id?}") {
            val id = (call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            ))
            while(result.next()){

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
            call.respond(patient)
        }
        post(/*"{surename?}"*/) {
                val patient = call.receive<Patient>()
            val surename = patient.Surename
            val query = connections.prepareStatement("INSERT INTO public.\"Patient\" (\"Surename\") VALUES ('$surename')")
            query.execute()
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = (call.parameters["id"] ?: return@delete call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            ))
            var ids = id.toInt()
            val query = connections.prepareStatement("DELETE FROM public.\"Patient\" WHERE id=$ids")
            query.execute()
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
    }
}