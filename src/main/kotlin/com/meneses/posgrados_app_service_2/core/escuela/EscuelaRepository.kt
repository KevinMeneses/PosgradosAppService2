package com.meneses.posgrados_app_service_2.core.escuela


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.Connection

class EscuelaRepository(
    private val connection: Connection,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getById(id: Int): Escuela = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_ESCUELA_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            return@withContext Escuela(
                id = resultSet.getInt("id"),
                director = resultSet.getString("director"),
                descripcion = resultSet.getString("descripcion"),
                correo = resultSet.getString("correo"),
                direccion = resultSet.getString("direccion"),
                latitud = resultSet.getDouble("latitud"),
                longitud = resultSet.getDouble("longitud")
            )
        } else {
            throw Exception("Record not found")
        }
    }

    private companion object {
        const val SELECT_ESCUELA_BY_ID = "SELECT * FROM escuela WHERE id=?"
    }
}