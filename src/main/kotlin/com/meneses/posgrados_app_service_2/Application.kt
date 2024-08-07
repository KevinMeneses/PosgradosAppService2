package com.meneses.posgrados_app_service_2

import com.meneses.posgrados_app_service_2.plugins.*
import io.ktor.server.application.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureDI()
    configureSecurity()
    configureRouting()
}
