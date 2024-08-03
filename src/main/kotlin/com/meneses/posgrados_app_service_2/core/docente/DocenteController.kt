package com.meneses.posgrados_app_service_2.core.docente

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.docenteController() {
    val docenteService: DocenteService by inject()

    route("/docente") {
        get("/{id}") {
            val id = call.parameters["id"]?.toInt()
            val docente = docenteService.getById(id)
            call.respond(docente)
        }

        get("/all") {
            val idPosgrado = call.parameters["id_posgrado"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val semester = call.parameters["semestre"]?.toInt()
            val docentes = docenteService.getMany(idPosgrado, semester)
            call.respond(docentes)
        }

        staticResources("/resources", "static", "1.jpg")
    }
}