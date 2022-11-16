package com.tvmedicine.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.tvmedicine.models.UsersSModel
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
    var usersSModelStorage = mutableListOf<UsersSModel>()
    //val secret = environment.config.property("jwt.secret").getString()
    //val issuer = environment.config.property("jwt.issuer").getString()
    //val audience = environment.config.property("jwt.audience").getString()
    //val myRealm = environment.config.property("jwt.realm").getString()
    routing {
            get("/") {
                call.respondText("Hello World!")
            }
        get("/user_type") {
            call.respondText("Hello World!")
        }
            post("/login") {
                val user = call.receive<User>()
                usersSModelStorage = dbUtils.getPatientByLogin(user.login)
                if(usersSModelStorage[0].password==user.password) {
                    val token = JWT.create()
                        .withAudience("https://tvmed.herokuapp.com/")
                        .withIssuer("https://tvmed.herokuapp.com/")
                        .withClaim("login", user.login)
                        .withExpiresAt(Date(System.currentTimeMillis() + 6000000000))
                        .sign(Algorithm.HMAC256("secret"))
                    call.respond(hashMapOf("token" to token, "user_type" to usersSModelStorage[0].user_type, "user_id" to usersSModelStorage[0].id.toString()))
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
