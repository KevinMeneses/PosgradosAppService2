package com.meneses.posgrados_app_service_2.core.calificacion

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.Connection

class CalificacionRepository(
    private val connection: Connection,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getAllByPosgradoSemesterAndUsuario(
        idUsuario: String,
        idPosgrado: Int,
        semester: Int
    ): List<Calificacion> = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_BY_POSGRADO_SEMESTER_USUARIO)
        statement.setString(1, idUsuario)
        statement.setInt(2, idPosgrado)
        statement.setInt(3, semester)
        val resultSet = statement.executeQuery()
        val calificaciones = mutableListOf<Calificacion>()

        while (resultSet.next()) {
            calificaciones.add(
                Calificacion(
                    id = resultSet.getInt("id"),
                    idUsuario = resultSet.getString("id_usuario"),
                    idDocente = resultSet.getInt("id_docente"),
                    calificacion = resultSet.getFloat("calificacion"),
                    promedio = resultSet.getFloat("promedio")
                )
            )
        }

        return@withContext calificaciones
    }

    suspend fun upsertCalificacion(
        calificacion: Float,
        idUsuario: String,
        idDocente: Int
    ): Unit = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(UPSERT_CALIFICACION)
        statement.setFloat(1, calificacion)
        statement.setString(2, idUsuario)
        statement.setInt(3, idDocente)
        statement.executeUpdate()
    }

    private companion object {
        const val SELECT_BY_POSGRADO_SEMESTER_USUARIO =
            "WITH promedio_docente AS (\n" +
                "\tSELECT id_docente, AVG(calificacion) as promedio FROM calificacion\n" +
                "\tGROUP BY id_docente\n" +
                ")\n" +
            "SELECT calificacion.id, calificacion.id_usuario, calificacion.id_docente, calificacion, promedio_docente.promedio FROM calificacion\n" +
            "JOIN docente ON calificacion.id_docente = docente.id\n" +
            "JOIN modulo ON docente.id = modulo.id_docente\n" +
            "JOIN promedio_docente ON promedio_docente.id_docente = docente.id\n" +
            "WHERE calificacion.id_usuario = ?\n" +
            "AND modulo.id_posgrado = ?\n" +
            "AND modulo.semestre = ?"

        const val UPSERT_CALIFICACION  =
            "INSERT INTO calificacion (calificacion, id_usuario, id_docente)\n" +
            "VALUES (?,?,?) ON CONFLICT (id_usuario, id_docente)\n" +
            "DO UPDATE SET calificacion = EXCLUDED.calificacion"
    }
}