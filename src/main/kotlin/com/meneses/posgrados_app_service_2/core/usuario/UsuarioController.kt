package com.meneses.posgrados_app_service_2.core.usuario

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.usuarioController() {
    val usuarioRepository: UsuarioRepository by inject()

    route("/usuario") {
        get("/sso") {
            val correo = call.parameters["correo"] ?: throw IllegalArgumentException("Invalid correo")
            val usuario = usuarioRepository.getByCorreo(correo)
            call.respond(usuario)
        }
    }
}