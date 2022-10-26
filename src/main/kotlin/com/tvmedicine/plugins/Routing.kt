package com.tvmedicine.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.tvmedicine.models.PatientSModel
import com.tvmedicine.models.Responds
import com.tvmedicine.models.User
import com.tvmedicine.routes.patientRouting
import com.tvmedicine.routes.treatmentRouting
import com.tvmedicine.utils.dbUtils
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRouting() {
    var patientSModelStorage = mutableListOf<PatientSModel>()
    //val secret = environment.config.property("jwt.secret").getString()
    //val issuer = environment.config.property("jwt.issuer").getString()
    //val audience = environment.config.property("jwt.audience").getString()
    //val myRealm = environment.config.property("jwt.realm").getString()
    routing {
            get("/") {
                call.respondText("Hello World!")
            }
            post("/login") {
                val user = call.receive<User>()
                patientSModelStorage = dbUtils.getPatientByLogin(user.login)
                if(patientSModelStorage[0].password==user.password) {
                    val token = JWT.create()
                        .withAudience("http://127.0.0.1:8080/")
                        .withIssuer("http://127.0.0.1:8080/")
                        .withClaim("login", user.login)
                        .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                        .sign(Algorithm.HMAC256("secret"))
                    call.respond(hashMapOf("token" to token))
                }
                else
                {
                    Responds.CredentialError(call)
                }
            }
        authenticate("auth-jwt") {
            patientRouting()
            treatmentRouting()
        }
    }
}
