package com.meneses.posgrados_app_service_2.core.horario

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.horarioController() {
    val horarioRepository: HorarioRepository by inject()

    route("/horario") {
        get("/all") {
            val idPosgrado = call.parameters["id_posgrado"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val semester = call.parameters["semestre"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val horarios = horarioRepository.getAllByPosgradoAndSemester(idPosgrado, semester)
            call.respond(horarios)
        }
    }
}