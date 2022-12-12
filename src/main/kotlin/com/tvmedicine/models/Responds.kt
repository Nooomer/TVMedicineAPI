package com.tvmedicine.models

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

interface Responds {
    companion object {
        suspend fun NotFoundError(name: String, call: ApplicationCall) {
            call.respondText("No $name found", status = HttpStatusCode.NotFound)
        }

        suspend fun CredentialError(call: ApplicationCall) {
            call.respondText("Login or password incorrect", status = HttpStatusCode.Unauthorized)
        }

        suspend fun Created(name: String, call: ApplicationCall) {
            call.respondText("$name stored correctly", status = HttpStatusCode.Created)
        }

        suspend fun NeedsParams(params: String, call: ApplicationCall) {
            call.respondText("Need more parameters: $params", status = HttpStatusCode.BadRequest)
        }
    }
}