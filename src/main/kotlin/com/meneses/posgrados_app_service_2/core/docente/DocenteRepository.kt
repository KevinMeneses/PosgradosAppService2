package com.meneses.posgrados_app_service_2.core.docente

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet

class DocenteRepository(
    private val connection: Connection,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getById(id: Int): Docente = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_DOCENTE_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next()) {
            return@withContext resultSet.toDocente()
        } else {
            throw Exception("Record not found")
        }
    }

    suspend fun getAllByPosgrado(idPosgrado: Int): List<Docente> = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_DOCENTES_BY_POSGRADO_ID)
        statement.setInt(1, idPosgrado)
        val resultSet = statement.executeQuery()
        val docentes = mutableListOf<Docente>()

        while (resultSet.next()) {
            docentes.add(resultSet.toDocente())
        }

        return@withContext docentes
    }

    suspend fun getAllByPosgradoAndSemester(
        idPosgrado: Int,
        semester: Int
    ): List<Docente> = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_DOCENTES_BY_POSGRADO_ID_AND_SEMESTER)
        statement.setInt(1, idPosgrado)
        statement.setInt(2, semester)
        val resultSet = statement.executeQuery()
        val docentes = mutableListOf<Docente>()

        while (resultSet.next()) {
            docentes.add(resultSet.toDocente())
        }

        return@withContext docentes
    }

    suspend fun getByModulo(idModulo: Int): Docente = withContext(ioDispatcher) {
        val statement = connection.prepareStatement(SELECT_DOCENTE_BY_MODULO)
        statement.setInt(1, idModulo)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            return@withContext resultSet.toDocente()
        } else {
            throw Exception("Record not found")
        }
    }

    private fun ResultSet.toDocente() = Docente(
        id = getInt("id"),
        nombre = getString("nombre"),
        apellido = getString("apellido"),
        profesion = getString("profesion"),
        descripcion = getString("descripcion"),
        imagen = getString("imagen")
    )

    private companion object {
        const val SELECT_DOCENTE_BY_ID = "SELECT * FROM docente WHERE id=?"

        const val SELECT_DOCENTES_BY_POSGRADO_ID =
            "SELECT * FROM public.docente \n" +
            "JOIN public.modulo \n" +
            "ON public.docente.id = public.modulo.id_docente\n" +
            "WHERE public.modulo.id_posgrado = ?"

        const val SELECT_DOCENTES_BY_POSGRADO_ID_AND_SEMESTER =
            "SELECT * FROM public.docente \n" +
            "JOIN public.modulo \n" +
            "ON public.docente.id = public.modulo.id_docente\n" +
            "WHERE public.modulo.id_posgrado = ?\n" +
            "AND public.modulo.semestre = ?"

        const val SELECT_DOCENTE_BY_MODULO =
            "SELECT * FROM public.docente \n" +
            "JOIN public.modulo \n" +
            "ON public.docente.id = public.modulo.id_docente\n" +
            "WHERE public.modulo.id = ?"
    }
}