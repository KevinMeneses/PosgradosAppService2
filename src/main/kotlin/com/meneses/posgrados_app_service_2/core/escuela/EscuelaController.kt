package com.meneses.posgrados_app_service_2.core.escuela

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.escuelaController() {
    val escuelaRepository: EscuelaRepository by inject()

    route("/escuela") {
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val escuela = escuelaRepository.getById(id)
            call.respond(escuela)
        }
    }
}