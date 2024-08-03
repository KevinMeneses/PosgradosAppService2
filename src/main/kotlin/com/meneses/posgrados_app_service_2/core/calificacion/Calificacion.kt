package com.meneses.posgrados_app_service_2.core.calificacion

import kotlinx.serialization.Serializable

@Serializable
data class Calificacion(
    val id: Int,
    val idUsuario: String,
    val idDocente: Int,
    val calificacion: Float,
    val promedio: Float
)
