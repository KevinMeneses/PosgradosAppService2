package com.meneses.posgrados_app_service_2.aggregate.dashboard

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.dashboardController() {
    val dashboardService: DashboardService by inject()

    authenticate("auth-jwt") {
        route("/dashboard") {
            get {
                val idUsuario = call.parameters["id_usuario"]
                val idPosgrado = call.parameters["id_posgrado"]?.toIntOrNull()

                if (idUsuario == null || idPosgrado == null) {
                    throw Exception("Invalid parameters")
                }

                try {
                    val dashboard = dashboardService.getDashboard(idUsuario, idPosgrado)
                    call.respond(dashboard)
                } catch (e: Exception) {
                    call.respond(e.stackTraceToString())
                }
            }
        }
    }
}