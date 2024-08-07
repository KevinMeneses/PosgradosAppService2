package com.meneses.posgrados_app_service_2.core.usuario

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet

class UsuarioRepository(
    private val connection: Connection,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getByCorreo(correo: String) = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_USUARIO_BY_CORREO)
        statement.setString(1, correo)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            return@withContext resultSet.toUsuario()
        } else {
            throw Exception("Record not found")
        }
    }

    suspend fun getByCodigo(codigo: String) = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_USUARIO_BY_CODIGO)
        statement.setString(1, codigo)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            return@withContext resultSet.toUsuario()
        } else {
            throw Exception("Record not found")
        }
    }

    private fun ResultSet.toUsuario() = Usuario(
        codigo = getString("codigo"),
        nombre = getString("nombre"),
        apellido = getString("apellido"),
        correo = getString("correo"),
        idPosgrado = getInt("id_posgrado"),
        semestre = getInt("semestre")
    )

    suspend fun saveRefreshToken(
        token: String,
        usuario: String
    ) = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(INSERT_TOKEN)
        statement.setString(1, token)
        statement.setString(2, usuario)
        statement.executeUpdate()
    }

    suspend fun getTokenByUsuario(usuario: String) = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_TOKEN_BY_USUARIO)
        statement.setString(1, usuario)
        val resultSet = statement.executeQuery()
        return@withContext if (resultSet.next()) {
            resultSet.getString("token")
        } else null
    }

    suspend fun getUsuarioByToken(token: String) = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_TOKEN_USUARIO_BY_TOKEN)
        statement.setString(1, token)
        val resultSet = statement.executeQuery()
        return@withContext if (resultSet.next()) {
            resultSet.getString("usuario")
        } else null
    }

    suspend fun deleteToken(refreshToken: String) = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(DELETE_TOKEN_BY_TOKEN)
        statement.setString(1, refreshToken)
        statement.execute()
    }

    private companion object {
        const val SELECT_USUARIO_BY_CORREO = "SELECT * FROM usuario WHERE correo=?"
        const val SELECT_USUARIO_BY_CODIGO = "SELECT * FROM usuario WHERE codigo=?"
        const val INSERT_TOKEN = "INSERT INTO token (token, usuario) VALUES (?,?)"
        const val SELECT_TOKEN_USUARIO_BY_TOKEN = "SELECT usuario FROM token WHERE token=?"
        const val SELECT_TOKEN_BY_USUARIO = "SELECT token FROM token WHERE usuario=?"
        const val DELETE_TOKEN_BY_TOKEN = "DELETE FROM token WHERE token=?"
    }
}