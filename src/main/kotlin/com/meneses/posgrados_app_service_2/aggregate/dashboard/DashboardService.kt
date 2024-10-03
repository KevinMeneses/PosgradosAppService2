package com.meneses.posgrados_app_service_2.aggregate.dashboard

import com.meneses.posgrados_app_service_2.core.calificacion.CalificacionService
import com.meneses.posgrados_app_service_2.core.docente.Docente
import com.meneses.posgrados_app_service_2.core.docente.DocenteService
import com.meneses.posgrados_app_service_2.core.horario.Horario
import com.meneses.posgrados_app_service_2.core.horario.HorarioService
import com.meneses.posgrados_app_service_2.core.modulo.Modulo
import com.meneses.posgrados_app_service_2.core.modulo.ModuloService
import com.meneses.posgrados_app_service_2.core.posgrado.Posgrado
import com.meneses.posgrados_app_service_2.core.posgrado.PosgradoRepository
import com.meneses.posgrados_app_service_2.core.usuario.UsuarioRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class DashboardService(
    private val posgradoRepository: PosgradoRepository,
    private val moduloService: ModuloService,
    private val docenteService: DocenteService,
    private val horarioService: HorarioService,
    private val calificacionService: CalificacionService,
    private val usuarioRepository: UsuarioRepository,
    ioDispatcher: CoroutineDispatcher
) {

    private val scope = CoroutineScope(ioDispatcher)

    suspend fun getDashboard(
        idUsuario: String,
        idPosgrado: Int
    ): Dashboard {
        val modulos = moduloService.getMany(idPosgrado)
        val semesters = modulos.map { it.semestre }

        val (posgrado, currentSemester) = listOf(
            scope.async { posgradoRepository.getById(idPosgrado) },
            scope.async { usuarioRepository.getByCodigo(idUsuario).semestre },
        ).awaitAll()

        return Dashboard(
            posgrado = posgrado as Posgrado,
            section = getSection(semesters, modulos, idUsuario),
            currentSemestre = currentSemester as Int
        )
    }

    private suspend fun DashboardService.getSection(
        semesters: List<Int>,
        modulos: List<Modulo>,
        idUsuario: String
    ) = semesters.associateWith { semestre ->
        val sectionModulos = modulos.filter { it.semestre == semestre }
        val (sectionDocentes, sectionHorarios) = listOf(
            getDocentes(sectionModulos),
            getHorarios(sectionModulos)
        ).awaitAll()

        Dashboard.Section(
            modulos = sectionModulos,
            docentes = sectionDocentes as List<Docente>,
            horarios = sectionHorarios as List<Horario>,
            calificaciones = getCalificaciones(
                idUsuario = idUsuario,
                docentes = sectionDocentes
            )
        )
    }

    private suspend fun getDocentes(modulos: List<Modulo>) =
        scope.async {
            modulos.map {
                scope.async {
                    docenteService.getByModulo(it.id)
                }
            }.awaitAll()
        }


    private suspend fun getHorarios(modulos: List<Modulo>) =
        scope.async {
            modulos.map {
                scope.async {
                    horarioService.getByModulo(it.id)
                }
            }.awaitAll()
                .filterNotNull()
        }


    private suspend fun getCalificaciones(idUsuario: String, docentes: List<Docente>) =
        docentes.map {
            scope.async {
                calificacionService.getByPosgradoAndDocente(idUsuario, it.id)
            }
        }.awaitAll()
            .filterNotNull()
}