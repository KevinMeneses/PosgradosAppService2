package com.meneses.posgrados_app_service_2.core.usuario

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Routing.usuarioController() {
    val usuarioRepository: UsuarioRepository by inject()
    val jwtSecret = environment?.config?.property("jwt.secret")?.getString()
    val jwtIssuer = environment?.config?.property("jwt.issuer")?.getString()
    val jwtAudience = environment?.config?.property("jwt.audience")?.getString()

    route("/usuario") {
        get("/sso") {
            val correo = call.parameters["correo"] ?: throw IllegalArgumentException("Invalid correo")
            val usuario = usuarioRepository.getByCorreo(correo).toJson()

            val token = JWT.create()
                .withAudience(jwtAudience)
                .withIssuer(jwtIssuer)
                .withClaim("usuario", usuario)
                .withExpiresAt(Date(System.currentTimeMillis() + 15 * 60000))
                .sign(Algorithm.HMAC256(jwtSecret))

            call.respond(hashMapOf("token" to token))
        }
    }
}