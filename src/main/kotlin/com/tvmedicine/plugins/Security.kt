package com.tvmedicine.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
    //val secret = environment.config.property("jwt.secret").getString()
    //val issuer = environment.config.property("jwt.issuer").getString()
    //val audience = environment.config.property("jwt.audience").getString()
    //val myRealm = environment.config.property("jwt.realm").getString()
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "Access"
            verifier(
                JWT
                .require(Algorithm.HMAC256("secret"))
                .withAudience("http://127.0.0.1:8080/")
                .withIssuer("http://127.0.0.1:8080/")
                .build())
            validate { credential ->
                if (credential.payload.getClaim("login").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }

    }
}
