package com.meneses.posgrados_app_service_2.core.horario

import kotlinx.serialization.Serializable

@Serializable
data class Horario(
    val id: Int,
    val idModulo: Int,
    val dia: String,
    val horaInicio: String,
    val horaFin: String,
    val sede: String,
    val salon: String
)
