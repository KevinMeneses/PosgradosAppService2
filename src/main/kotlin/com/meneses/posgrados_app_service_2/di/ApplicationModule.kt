package com.meneses.posgrados_app_service_2.di

import com.meneses.posgrados_app_service_2.aggregate.authentication.AuthenticationService
import com.meneses.posgrados_app_service_2.aggregate.authentication.JwtService
import com.meneses.posgrados_app_service_2.aggregate.dashboard.DashboardService
import com.meneses.posgrados_app_service_2.core.calificacion.CalificacionRepository
import com.meneses.posgrados_app_service_2.core.calificacion.CalificacionService
import com.meneses.posgrados_app_service_2.core.docente.DocenteRepository
import com.meneses.posgrados_app_service_2.core.docente.DocenteService
import com.meneses.posgrados_app_service_2.core.escuela.EscuelaRepository
import com.meneses.posgrados_app_service_2.core.horario.HorarioRepository
import com.meneses.posgrados_app_service_2.core.horario.HorarioService
import com.meneses.posgrados_app_service_2.core.modulo.ModuloRepository
import com.meneses.posgrados_app_service_2.core.modulo.ModuloService
import com.meneses.posgrados_app_service_2.core.posgrado.PosgradoRepository
import com.meneses.posgrados_app_service_2.core.usuario.UsuarioRepository
import com.meneses.posgrados_app_service_2.plugins.connectToPostgres
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

fun Application.applicationModule() = module {
    single { this@applicationModule }
    single { connectToPostgres(false) }
    single { Dispatchers.IO }
    single { JwtService(get(), get()) }
}

val calificacionModule get() = module {
    single { CalificacionRepository(get(), get()) }
    single { CalificacionService(get()) }
}

val docenteModule = module {
    single { DocenteRepository(get(), get()) }
    single { DocenteService(get()) }
}

val escuelaModule = module {
    single { EscuelaRepository(get(), get()) }
}

val horarioModule = module {
    single { HorarioRepository(get(), get()) }
    single { HorarioService(get()) }
}

val moduloModule = module {
    single { ModuloRepository(get(), get()) }
    single { ModuloService(get()) }
}

val posgradoModule = module {
    single { PosgradoRepository(get(), get()) }
}

val usuarioModule = module {
    single { UsuarioRepository(get(), get()) }
}

val dashboardModule = module {
    single { DashboardService(get(), get(), get(), get(), get(), get(), get()) }
}

val authenticationModule = module {
    single { AuthenticationService(get(), get()) }
}