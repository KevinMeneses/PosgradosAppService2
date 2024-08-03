package com.meneses.posgrados_app_service_2.core.calificacion

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.calificacionController() {
    val calificacionRepository: CalificacionRepository by inject()

    route("calificacion") {
        get("/all") {
            val idUsuario = call.parameters["id_usuario"]
            val idPosgrado = call.parameters["id_posgrado"]?.toIntOrNull()
            val semester = call.parameters["semestre"]?.toIntOrNull()

            if (idUsuario == null || idPosgrado == null || semester == null) {
                throw Exception("Invalid parameters")
            }

            val calificaciones = calificacionRepository.getAllByPosgradoSemesterAndUsuario(idUsuario, idPosgrado, semester)
            call.respond(calificaciones)
        }

        put("/upsert") {
            val calificacion = call.receive<CalificacionDTO>()
            calificacionRepository.upsertCalificacion(
                calificacion = calificacion.calificacion,
                idUsuario = calificacion.idUsuario,
                idDocente = calificacion.idDocente
            )
            call.respond(HttpStatusCode.Created)
        }
    }

}