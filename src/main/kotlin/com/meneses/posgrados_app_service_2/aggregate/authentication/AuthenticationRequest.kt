package com.meneses.posgrados_app_service_2.aggregate.authentication

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRequest(
    val correo: String
)
