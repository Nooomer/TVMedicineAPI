package com.tvmedicine.plugins

import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest

fun getMd5Digest(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
fun Application.configureSecurity() {
    install(Authentication) {
        jwt {
            val privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAtfJaLrzXILUg1U3N1KV8yJr92GHn5OtYZR7qWk1Mc4cy4JGjklYup7weMjBD9f3bBVoIsiUVX6xNcYIr0Ie0AQIDAQABAkEAg+FBquToDeYcAWBe1EaLVyC45HG60zwfG1S4S3IB+y4INz1FHuZppDjBh09jptQNd+kSMlG1LkAc/3znKTPJ7QIhANpyB0OfTK44lpH4ScJmCxjZV52mIrQcmnS3QzkxWQCDAiEA1Tn7qyoh+0rOO/9vJHP8U/beo51SiQMw0880a1UaiisCIQDNwY46EbhGeiLJR1cidr+JHl86rRwPDsolmeEF5AdzRQIgK3KXL3d0WSoS//K6iOkBX3KMRzaFXNnDl0U/XyeGMuUCIHaXv+n+Brz5BDnRbWS+2vkgIe9bUNlkiArpjWvX+2we"
            val issuer = "http://0.0.0.0:8080/"
            val audience = "http://0.0.0.0:8080/hello"
            realm = "Access to 'hello'"
        }
        }

    }
}
