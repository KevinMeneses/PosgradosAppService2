package com.meneses.posgrados_app_service_2.core.usuario

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val codigo: String,
    val nombre: String,
    val apellido: String,
    val correo: String?,
    val idPosgrado: Int,
    val semestre: Int
)