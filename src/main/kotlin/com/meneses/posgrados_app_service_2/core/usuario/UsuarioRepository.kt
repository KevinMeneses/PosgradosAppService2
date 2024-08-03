package com.meneses.posgrados_app_service_2.core.usuario

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.Connection

class UsuarioRepository(
    private val connection: Connection,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getByCorreo(correo: String) = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_USUARIO_BY_CORREO)
        statement.setString(1, correo)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            return@withContext Usuario(
                codigo = resultSet.getString("codigo"),
                nombre = resultSet.getString("nombre"),
                apellido = resultSet.getString("apellido"),
                correo = resultSet.getString("correo"),
                idPosgrado = resultSet.getInt("id_posgrado"),
                semestre = resultSet.getInt("semestre")
            )
        } else {
            throw Exception("Record not found")
        }
    }

    private companion object {
        const val SELECT_USUARIO_BY_CORREO = "SELECT * FROM usuario WHERE correo=?"
    }
}