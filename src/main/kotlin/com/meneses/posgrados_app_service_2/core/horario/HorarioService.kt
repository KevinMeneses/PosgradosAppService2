package com.meneses.posgrados_app_service_2.core.horario

class HorarioService(
    private val horarioRepository: HorarioRepository
) {
    suspend fun getMany(idPosgrado: Int?, semester: Int? = null): List<Horario> {
        idPosgrado ?: throw IllegalArgumentException("Invalid ID")
        return if (semester == null) {
            horarioRepository.getAllByPosgrado(idPosgrado)
        } else {
            horarioRepository.getAllByPosgradoAndSemester(idPosgrado, semester)
        }
    }

    suspend fun getByModulo(idModulo: Int): Horario? {
        return horarioRepository.getByModulo(idModulo)
    }
}