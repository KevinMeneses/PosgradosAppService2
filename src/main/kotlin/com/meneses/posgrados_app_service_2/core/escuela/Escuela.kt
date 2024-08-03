package com.meneses.posgrados_app_service_2.core.escuela

import kotlinx.serialization.Serializable

@Serializable
data class Escuela(
    val id: Int,
    val director: String,
    val descripcion: String,
    val correo: String,
    val direccion: String,
    val latitud: Double,
    val longitud: Double
)
