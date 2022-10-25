package com.tvmedicine.plugins

import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest

fun getMd5Digest(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
fun Application.configureSecurity() {
    install(Authentication) {
        digest("auth-digest") {
            // Configure digest authentication
            val myRealm = "Access to the '/' path"
            val userTable: Map<String, ByteArray> = mapOf(
                "89534500038" to getMd5Digest("89534500038:$myRealm:1234567890"),
                "admin" to getMd5Digest("admin:$myRealm:password")
            )
            realm = myRealm
            digestProvider { userName, realm ->
                userTable[userName]
            }
        }

    }
}
