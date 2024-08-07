package com.meneses.posgrados_app_service_2.core.usuario

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Usuario(
    val codigo: String,
    val nombre: String,
    val apellido: String,
    val correo: String?,
    val idPosgrado: Int,
    val semestre: Int
) {
    fun toJson(): String = Json.encodeToString(this)

    companion object {
        fun fromJson(json: String) = Json.decodeFromString<Usuario>(json)
    }
}