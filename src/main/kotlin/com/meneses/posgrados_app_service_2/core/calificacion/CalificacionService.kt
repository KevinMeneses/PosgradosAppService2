package com.meneses.posgrados_app_service_2.core.calificacion

class CalificacionService(
    private val calificacionRepository: CalificacionRepository
) {
    suspend fun getMany(
        idUsuario: String?,
        idPosgrado: Int?,
        semester: Int? = null
    ): List<Calificacion> {
        if (idUsuario == null || idPosgrado == null) {
            throw IllegalArgumentException("Invalid ID")
        }

        return if (semester == null) {
            calificacionRepository.getAllByPosgradoAndUsuario(idUsuario, idPosgrado)
        } else {
            calificacionRepository.getAllByPosgradoSemesterAndUsuario(idUsuario, idPosgrado, semester)
        }
    }

    suspend fun getByPosgradoAndDocente(idUsuario: String, idDocente: Int): Calificacion? {
        return calificacionRepository.getByPosgradoAndDocente(idUsuario, idDocente)
    }

    suspend fun upsertCalificacion(calificacion: Float, idUsuario: String, idDocente: Int) =
        calificacionRepository.upsertCalificacion(calificacion, idUsuario, idDocente)
}