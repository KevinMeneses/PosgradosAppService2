package com.meneses.posgrados_app_service_2.aggregate.authentication

import com.meneses.posgrados_app_service_2.core.usuario.Usuario
import com.meneses.posgrados_app_service_2.core.usuario.UsuarioRepository

class AuthenticationService(
    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService
) {
    suspend fun authenticateWithCorreo(correo: String): AuthenticationResponse {
        val usuario = usuarioRepository.getByCorreo(correo)

        val accessToken = jwtService.createToken(usuario.codigo)
        val refreshToken = getRefreshTokenOrCreateIt(usuario.toJson())

        return AuthenticationResponse(accessToken, refreshToken)
    }

    private suspend fun getRefreshTokenOrCreateIt(usuario: String): String {
        var refreshToken = usuarioRepository.getTokenByUsuario(usuario)

        if (refreshToken == null || !jwtService.isRefreshTokenValid(refreshToken)) {
            refreshToken = jwtService.createRefreshToken(usuario)
            usuarioRepository.saveRefreshToken(refreshToken, usuario)
        }

        return refreshToken
    }

    suspend fun refreshAccessToken(refreshToken: String): AuthenticationResponse {
        val usuarioJson = usuarioRepository.getUsuarioByToken(refreshToken)!!
        val usuario = Usuario.fromJson(usuarioJson)

        usuarioRepository.deleteToken(refreshToken)

        val accessToken = jwtService.createToken(usuario.codigo)
        val newRefreshToken = jwtService.createRefreshToken(usuarioJson)

        usuarioRepository.saveRefreshToken(newRefreshToken, usuarioJson)

        return AuthenticationResponse(accessToken, newRefreshToken)
    }
}