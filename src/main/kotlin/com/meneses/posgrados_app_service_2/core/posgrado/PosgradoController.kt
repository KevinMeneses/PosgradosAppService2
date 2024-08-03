package com.meneses.posgrados_app_service_2.core.posgrado

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.posgradoController() {
    val posgradoRepository: PosgradoRepository by inject()

    route("/posgrado") {
        get("/all") {
            val posgrados = posgradoRepository.getAll()
            call.respond(posgrados)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val posgrado = posgradoRepository.getById(id)
            call.respond(posgrado)
        }
    }
}