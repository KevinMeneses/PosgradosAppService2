package com.meneses.posgrados_app_service_2.core.calificacion

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.calificacionController() {
    val calificacionService: CalificacionService by inject()

    authenticate("auth-jwt") {
        route("calificacion") {
            get("/all") {
                val idUsuario = call.parameters["id_usuario"]
                val idPosgrado = call.parameters["id_posgrado"]?.toIntOrNull()
                val semester = call.parameters["semestre"]?.toIntOrNull()

                val calificaciones = calificacionService.getMany(
                    idUsuario = idUsuario,
                    idPosgrado = idPosgrado,
                    semester = semester
                )

                call.respond(calificaciones)
            }


            put("/upsert") {
                val calificacion = call.receive<CalificacionDTO>()
                calificacionService.upsertCalificacion(
                    calificacion = calificacion.calificacion,
                    idUsuario = calificacion.idUsuario,
                    idDocente = calificacion.idDocente
                )
                call.respond(HttpStatusCode.Created)
            }
        }
    }
}