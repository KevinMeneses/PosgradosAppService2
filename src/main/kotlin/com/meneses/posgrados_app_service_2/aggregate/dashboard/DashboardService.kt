package com.meneses.posgrados_app_service_2.aggregate.dashboard

import com.meneses.posgrados_app_service_2.core.calificacion.Calificacion
import com.meneses.posgrados_app_service_2.core.calificacion.CalificacionRepository
import com.meneses.posgrados_app_service_2.core.docente.Docente
import com.meneses.posgrados_app_service_2.core.docente.DocenteService
import com.meneses.posgrados_app_service_2.core.horario.Horario
import com.meneses.posgrados_app_service_2.core.horario.HorarioRepository
import com.meneses.posgrados_app_service_2.core.modulo.Modulo
import com.meneses.posgrados_app_service_2.core.modulo.ModuloService
import com.meneses.posgrados_app_service_2.core.posgrado.Posgrado
import com.meneses.posgrados_app_service_2.core.posgrado.PosgradoRepository
import kotlinx.coroutines.*

class DashboardService(
    private val posgradoRepository: PosgradoRepository,
    private val moduloService: ModuloService,
    private val docenteService: DocenteService,
    private val horarioRepository: HorarioRepository,
    private val calificacionRepository: CalificacionRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getDashboard(
        idUsuario: String,
        idPosgrado: Int,
        semester: Int
    ): Dashboard = withContext(ioDispatcher) {
        val results = listOf(
            async { posgradoRepository.getById(idPosgrado) },
            async { moduloService.getMany(idPosgrado, semester) },
            async { docenteService.getMany(idPosgrado, semester) },
            async { horarioRepository.getAllByPosgradoAndSemester(idPosgrado, semester) },
            async { calificacionRepository.getAllByPosgradoSemesterAndUsuario(idUsuario, idPosgrado, semester) }
        ).awaitAll()

        return@withContext Dashboard(
            posgrado = results[0] as Posgrado,
            modulos = results[1] as List<Modulo>,
            docentes = results[2] as List<Docente>,
            horarios = results[3] as List<Horario>,
            calificaciones = results[4] as List<Calificacion>
        )
    }
}