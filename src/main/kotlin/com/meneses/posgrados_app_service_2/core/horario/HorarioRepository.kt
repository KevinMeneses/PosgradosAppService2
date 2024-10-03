package com.meneses.posgrados_app_service_2.core.horario

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet

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
            horarios.add(resultSet.toHorario())
        }

        return@withContext horarios
    }

    suspend fun getAllByPosgrado(
        idPosgrado: Int
    ): List<Horario> = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_BY_POSGRADO)
        statement.setInt(1, idPosgrado)
        val resultSet = statement.executeQuery()
        val horarios = mutableListOf<Horario>()

        while (resultSet.next()) {
            horarios.add(resultSet.toHorario())
        }

        return@withContext horarios
    }

    suspend fun getByModulo(
        idModulo: Int
    ): Horario? = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_BY_MODULO)
        statement.setInt(1, idModulo)
        val resultSet = statement.executeQuery()

        return@withContext if (resultSet.next()) {
            resultSet.toHorario()
        } else {
            null
        }
    }

    private fun ResultSet.toHorario() = Horario(
        id = getInt("id"),
        idModulo = getInt("id_modulo"),
        dia = getString("dia"),
        horaInicio = getString("hora_inicio"),
        horaFin = getString("hora_fin"),
        sede = getString("sede"),
        salon = getString("salon")
    )

    private companion object {
        const val SELECT_BY_POSGRADO_AND_SEMESTER =
            "SELECT * FROM horario " +
                "JOIN modulo ON horario.id_modulo = modulo.id " +
                "WHERE modulo.id_posgrado = ? " +
                "AND modulo.semestre = ?"

        const val SELECT_BY_POSGRADO =
            "SELECT * FROM horario " +
                "JOIN modulo ON horario.id_modulo = modulo.id " +
                "WHERE modulo.id_posgrado = ?"

        const val SELECT_BY_MODULO =
            "SELECT * FROM horario WHERE id_modulo = ?"
    }
}