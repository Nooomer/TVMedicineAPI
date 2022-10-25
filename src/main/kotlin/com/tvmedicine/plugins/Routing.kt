package com.tvmedicine.plugins

import com.tvmedicine.routes.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        authenticate("auth-digest") {
            get("/") {
                call.respondText("Hello World!")
            }

            patientRouting()
            treatmentRouting()
        }
    }
}
