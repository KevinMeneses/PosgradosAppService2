package com.meneses.posgrados_app_service_2.core.modulo

class ModuloService(
    private val moduloRepository: ModuloRepository
) {
    suspend fun getMany(idPosgrado: Int?, semester: Int?): List<Modulo> {
        idPosgrado ?: throw IllegalArgumentException("Invalid ID")
        return if (semester == null) {
            moduloRepository.getAllByPosgrado(idPosgrado)
        } else {
            moduloRepository.getAllByPosgradoAndSemester(idPosgrado, semester)
        }
    }
}