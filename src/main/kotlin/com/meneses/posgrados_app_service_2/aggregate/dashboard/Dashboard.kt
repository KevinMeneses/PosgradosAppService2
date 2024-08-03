package com.meneses.posgrados_app_service_2.aggregate.dashboard

import com.meneses.posgrados_app_service_2.core.calificacion.Calificacion
import com.meneses.posgrados_app_service_2.core.docente.Docente
import com.meneses.posgrados_app_service_2.core.horario.Horario
import com.meneses.posgrados_app_service_2.core.modulo.Modulo
import com.meneses.posgrados_app_service_2.core.posgrado.Posgrado
import kotlinx.serialization.Serializable

@Serializable
data class Dashboard(
    val posgrado: Posgrado,
    val modulos: List<Modulo>,
    val docentes: List<Docente>,
    val horarios: List<Horario>,
    val calificaciones: List<Calificacion>
)
