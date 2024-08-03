package com.meneses.posgrados_app_service_2.core.modulo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.Connection

class ModuloRepository(
    private val connection: Connection,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getAllByPosgrado(idPosgrado: Int) = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_BY_ID_POSGRADO)
        statement.setInt(1, idPosgrado)
        val resultSet = statement.executeQuery()
        val modulos = mutableListOf<Modulo>()

        while (resultSet.next()) {
            modulos.add(
                Modulo(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    descripcion = resultSet.getString("descripcion"),
                    creditos = resultSet.getInt("creditos"),
                    duracion = resultSet.getString("duracion"),
                    semestre = resultSet.getInt("semestre"),
                    idDocente = resultSet.getInt("id_docente"),
                    idPosgrado = resultSet.getInt("id_posgrado"),
                )
            )
        }

        return@withContext modulos
    }

    suspend fun getAllByPosgradoAndSemester(
        idPosgrado: Int,
        semester: Int
    ) = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_BY_ID_POSGRADO_AND_SEMESTER)
        statement.setInt(1, idPosgrado)
        statement.setInt(2, semester)
        val resultSet = statement.executeQuery()
        val modulos = mutableListOf<Modulo>()

        while (resultSet.next()) {
            modulos.add(
                Modulo(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    descripcion = resultSet.getString("descripcion"),
                    creditos = resultSet.getInt("creditos"),
                    duracion = resultSet.getString("duracion"),
                    semestre = resultSet.getInt("semestre"),
                    idDocente = resultSet.getInt("id_docente"),
                    idPosgrado = resultSet.getInt("id_posgrado"),
                )
            )
        }

        return@withContext modulos
    }

    private companion object {
        const val SELECT_BY_ID_POSGRADO = "SELECT * FROM modulo WHERE id_posgrado=? ORDER BY id ASC"
        const val SELECT_BY_ID_POSGRADO_AND_SEMESTER = "SELECT * FROM modulo WHERE id_posgrado=? AND semestre =? ORDER BY id ASC"
    }
}