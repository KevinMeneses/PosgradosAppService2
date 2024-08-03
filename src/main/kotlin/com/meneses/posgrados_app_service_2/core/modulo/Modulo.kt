package com.meneses.posgrados_app_service_2.core.modulo

import kotlinx.serialization.Serializable

@Serializable
data class Modulo(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val creditos: Int,
    val duracion: String,
    val semestre: Int,
    val idDocente: Int,
    val idPosgrado: Int
)
