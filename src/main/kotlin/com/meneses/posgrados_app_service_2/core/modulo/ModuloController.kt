package com.meneses.posgrados_app_service_2.core.modulo

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.moduloController() {
    val moduloService: ModuloService by inject()

    route("/modulo") {
        get("/all") {
            val idPosgrado = call.parameters["id_posgrado"]?.toIntOrNull()
            val semester = call.parameters["semestre"]?.toIntOrNull()
            val modulos = moduloService.getMany(idPosgrado, semester)
            call.respond(modulos)
        }
    }
}