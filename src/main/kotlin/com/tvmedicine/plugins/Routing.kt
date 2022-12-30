package com.tvmedicine.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.tvmedicine.models.UsersSModel
import com.tvmedicine.utils.Responds
import com.tvmedicine.models.LoginData
import com.tvmedicine.routes.*
import com.tvmedicine.utils.dbUtils
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRouting() {
    var usersSModelStorage: MutableList<UsersSModel>
    //val secret = environment.config.property("jwt.secret").getString()
    //val issuer = environment.config.property("jwt.issuer").getString()
    //val audience = environment.config.property("jwt.audience").getString()
    //val myRealm = environment.config.property("jwt.realm").getString()
    val appConfig = HoconApplicationConfig(ConfigFactory.load())
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/user_type") {
            call.respondText("Hello World!")
        }
        post("/login") {
            val loginData = call.receive<LoginData>()
            usersSModelStorage = dbUtils.getPatientByLogin(loginData.login)
            if (usersSModelStorage.size != 0) {
                if (usersSModelStorage[0].password == loginData.password) {
                    val token = JWT.create()
                        .withAudience(appConfig.property("ktor.jwt.audience").getString())
                        .withIssuer(appConfig.property("ktor.jwt.issuer").getString())
                        .withClaim("login", loginData.login)
                        .withExpiresAt(Date(System.currentTimeMillis() + 6000000000))
                        .sign(Algorithm.HMAC256(appConfig.property("ktor.jwt.secret").getString()))
                    call.respond(
                        hashMapOf(
                            "token" to token,
                            "user_type" to usersSModelStorage[0].user_type,
                            "user_id" to usersSModelStorage[0].id.toString()
                        )
                    )
                } else {
                    Responds.CredentialError(call)
                }
            } else {
                Responds.CredentialError(call)
            }
        }
        authenticate("auth-jwt") {
            patientRouting()
            treatmentRouting()
        }
    }
}
