package com.meneses.posgrados_app_service_2.core.horario

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.Connection

class HorarioRepository(
    private val connection: Connection,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getAllByPosgradoAndSemester(
        idPosgrado: Int,
        semester: Int
    ): List<Horario> = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_BY_POSGRADO_AND_SEMESTER)
        statement.setInt(1, idPosgrado)
        statement.setInt(2, semester)
        val resultSet = statement.executeQuery()
        val horarios = mutableListOf<Horario>()

        while (resultSet.next()) {
            horarios.add(
                Horario(
                    id = resultSet.getInt("id"),
                    idModulo = resultSet.getInt("id_modulo"),
                    dia = resultSet.getString("dia"),
                    horaInicio = resultSet.getString("hora_inicio"),
                    horaFin = resultSet.getString("hora_fin"),
                    sede = resultSet.getString("sede"),
                    salon = resultSet.getString("salon")
                )
            )
        }

        return@withContext horarios
    }

    private companion object {
        const val SELECT_BY_POSGRADO_AND_SEMESTER =
            "SELECT * FROM horario " +
            "JOIN modulo ON horario.id_modulo = modulo.id " +
            "WHERE modulo.id_posgrado = ? " +
            "AND modulo.semestre = ?"
    }
}