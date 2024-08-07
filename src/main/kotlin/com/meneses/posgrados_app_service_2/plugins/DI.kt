package com.meneses.posgrados_app_service_2.plugins

import com.meneses.posgrados_app_service_2.di.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(
            applicationModule(),
            calificacionModule,
            docenteModule,
            escuelaModule,
            horarioModule,
            moduloModule,
            posgradoModule,
            usuarioModule,
            dashboardModule,
            authenticationModule
        )
    }
}