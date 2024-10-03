package com.meneses.posgrados_app_service_2.core.horario

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.horarioController() {
    val horarioService: HorarioService by inject()

    route("/horario") {
        get("/all") {
            val idPosgrado = call.parameters["id_posgrado"]?.toIntOrNull()
            val semester = call.parameters["semestre"]?.toIntOrNull()
            val horarios = horarioService.getMany(idPosgrado, semester)
            call.respond(horarios)
        }
    }
}