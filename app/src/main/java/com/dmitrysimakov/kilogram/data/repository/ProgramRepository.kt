package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.remote.data_sources.ProgramSource

class ProgramRepository(
        private val dao: ProgramDao,
        private val src: ProgramSource
) {
    
    fun programsFlow() = dao.programsFlow()
    
    suspend fun insert(program: Program) {
        dao.insert(program)
        src.uploadProgram(program.id)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.deleteProgram(id)
    }
}