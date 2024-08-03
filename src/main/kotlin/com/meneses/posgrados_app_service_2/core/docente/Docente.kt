package com.meneses.posgrados_app_service_2.core.docente

import kotlinx.serialization.Serializable

@Serializable
data class Docente(
    var id: Int,
    var nombre: String,
    val apellido: String,
    val profesion: String,
    var descripcion: String,
    val imagen: String
)
