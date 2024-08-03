package com.meneses.posgrados_app_service_2.core.calificacion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalificacionDTO(
    val calificacion: Float,
    @SerialName("id_usuario")
    val idUsuario: String,
    @SerialName("id_docente")
    val idDocente: Int
)
