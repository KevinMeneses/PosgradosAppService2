package com.meneses.posgrados_app_service_2.plugins

import com.meneses.posgrados_app_service_2.aggregate.authentication.JwtService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val jwtService: JwtService by inject()

    authentication {
        jwt("auth-jwt") {
            realm = jwtService.jwtRealm
            verifier(jwtService.jwtVerifier)

            validate { credential ->
                jwtService.validateCredential(credential)
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
