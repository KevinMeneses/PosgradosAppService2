package com.meneses.posgrados_app_service_2.core.docente

class DocenteService(
    private val docenteRepository: DocenteRepository
) {
    suspend fun getById(id: Int?): Docente {
        id ?: throw IllegalArgumentException("Invalid ID")
        return docenteRepository.getById(id)
    }

    suspend fun getMany(idPosgrado: Int?, semester: Int?): List<Docente> {
        idPosgrado ?: throw IllegalArgumentException("Invalid ID")
        return if (semester == null) {
            docenteRepository.getAllByPosgrado(idPosgrado)
        } else {
            docenteRepository.getAllByPosgradoAndSemester(idPosgrado, semester)
        }
    }
}