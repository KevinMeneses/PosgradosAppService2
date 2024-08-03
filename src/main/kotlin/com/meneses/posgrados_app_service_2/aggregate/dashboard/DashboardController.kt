package com.meneses.posgrados_app_service_2.aggregate.dashboard

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.dashboardController() {
    val dashboardService: DashboardService by inject()

    route("/dashboard") {
        get {
            val idUsuario = call.parameters["id_usuario"]
            val idPosgrado = call.parameters["id_posgrado"]?.toIntOrNull()
            val semester = call.parameters["semestre"]?.toIntOrNull()

            if (idUsuario == null || idPosgrado == null || semester == null) {
                throw Exception("Invalid parameters")
            }

            val dashboard = dashboardService.getDashboard(idUsuario, idPosgrado, semester)
            call.respond(dashboard)
        }
    }
}