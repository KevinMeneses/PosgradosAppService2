package com.meneses.posgrados_app_service_2.aggregate.authentication

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponse(
    val token: String,
    val refreshToken: String
)
