package com.meneses.posgrados_app_service_2.core.posgrado

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.Connection

class PosgradoRepository(
    private val connection: Connection,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getById(id: Int): Posgrado = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_POSGRADO_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            return@withContext Posgrado(
                id = resultSet.getInt("id"),
                codSnies = resultSet.getString("cod_snies"),
                nombre = resultSet.getString("nombre"),
                duracion = resultSet.getString("duracion"),
                totalCreditos = resultSet.getInt("total_creditos"),
                descripcion = resultSet.getString("descripcion"),
                valorSemestre = resultSet.getString("valor_semestre")
            )
        } else {
            throw Exception("Record not found")
        }
    }

    suspend fun getAll(): List<Posgrado> = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_ALL_POSGRADO)
        val resultSet = statement.executeQuery()
        val posgrados = mutableListOf<Posgrado>()

        while (resultSet.next()) {
            val posgrado = Posgrado(
                id = resultSet.getInt("id"),
                codSnies = resultSet.getString("cod_snies"),
                nombre = resultSet.getString("nombre"),
                duracion = resultSet.getString("duracion"),
                totalCreditos = resultSet.getInt("total_creditos"),
                descripcion = resultSet.getString("descripcion"),
                valorSemestre = resultSet.getString("valor_semestre")
            )
            posgrados.add(posgrado)
        }

        return@withContext posgrados
    }

    private companion object {
        const val SELECT_ALL_POSGRADO = "SELECT * FROM posgrado ORDER BY id ASC"
        const val SELECT_POSGRADO_BY_ID = "SELECT * FROM posgrado WHERE id=?"
    }
}