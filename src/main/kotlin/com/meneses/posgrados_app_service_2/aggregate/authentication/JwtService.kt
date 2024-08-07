package com.meneses.posgrados_app_service_2.aggregate.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.meneses.posgrados_app_service_2.core.usuario.UsuarioRepository
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import java.util.*

class JwtService(
    private val application: Application,
    private val usuarioRepository: UsuarioRepository
) {
    private val jwtSecret = getProperty("jwt.secret")
    private val jwtIssuer = getProperty("jwt.issuer")
    private val jwtAudience = getProperty("jwt.audience")

    val jwtRealm = getProperty("jwt.realm")

    val jwtVerifier: JWTVerifier = JWT.require(Algorithm.HMAC256(jwtSecret))
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .build()

    suspend fun validateCredential(credential: JWTCredential): JWTPrincipal? {
        val codigo = extractCodigo(credential)
        usuarioRepository.getByCodigo(codigo)

        return if (credential.payload.audience.contains(jwtAudience)) {
            JWTPrincipal(credential.payload)
        } else {
            null
        }
    }

    fun isRefreshTokenValid(token: String): Boolean {
        val decodedJwt = decodedJWT(token)
        return decodedJwt?.audience?.first() == jwtAudience
    }

    private fun decodedJWT(token: String): DecodedJWT? =
        try {
            jwtVerifier.verify(token)
        } catch (e: Exception) {
            null
        }

    fun createToken(claim: String): String =
        createJwtToken(claim, 15 * 60000)

    fun createRefreshToken(claim: String): String =
        createJwtToken(claim, 4 * 60 * 60000)

    private fun createJwtToken(claim: String, expire: Int): String =
        JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim(CLAIM, claim)
            .withExpiresAt(Date(System.currentTimeMillis() + expire))
            .sign(Algorithm.HMAC256(jwtSecret))

    private fun extractCodigo(credential: JWTCredential) =
        credential.payload.getClaim(CLAIM).asString()

    private fun getProperty(key: String) =
        application.environment.config.property(key).getString()

    private companion object {
        const val CLAIM = "usuario"
    }
}