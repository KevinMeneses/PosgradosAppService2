package com.meneses.posgrados_app_service_2.aggregate.authentication

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.authenticationController() {
    val authenticationService: AuthenticationService by inject()

    route("/auth") {
        post("/sso") {
            val authRequest = call.receive<AuthenticationRequest>()
            val authResponse = authenticationService.authenticateWithCorreo(authRequest.correo)
            call.respond(authResponse)
        }

        post("/refresh") {
            val refreshTokenRequest = call.receive<RefreshTokenRequest>()
            val authResponse = authenticationService.refreshAccessToken(refreshTokenRequest.token)
            call.respond(authResponse)
        }
    }
}