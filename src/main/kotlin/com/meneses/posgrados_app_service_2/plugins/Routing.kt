package com.meneses.posgrados_app_service_2.plugins

import com.meneses.posgrados_app_service_2.aggregate.authentication.authenticationController
import com.meneses.posgrados_app_service_2.aggregate.dashboard.dashboardController
import com.meneses.posgrados_app_service_2.core.calificacion.calificacionController
import com.meneses.posgrados_app_service_2.core.docente.docenteController
import com.meneses.posgrados_app_service_2.core.escuela.escuelaController
import com.meneses.posgrados_app_service_2.core.horario.horarioController
import com.meneses.posgrados_app_service_2.core.modulo.moduloController
import com.meneses.posgrados_app_service_2.core.posgrado.posgradoController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        escuelaController()
        posgradoController()
        docenteController()
        authenticationController()
        moduloController()
        horarioController()
        calificacionController()
        dashboardController()
        /*install(SSE)
        sse("/flow") {
            var i = 0
            while (true) {
                delay(1000)
                send(ServerSentEvent(
                    id = "${i++}",
                    event = "info",
                    data = "event: $i"
                ))
            }
        }*/
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
}
