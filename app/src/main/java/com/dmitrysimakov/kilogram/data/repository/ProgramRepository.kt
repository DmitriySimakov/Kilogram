package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.data.remote.data_sources.ProgramSource

class ProgramRepository(
        private val dao: ProgramDao,
        private val src: ProgramSource
) {
    
    fun programsFlow() = dao.programsFlow()
    
    suspend fun program(id: Long) = dao.program(id)
    
    suspend fun insert(program: Program): Long {
        val id = dao.insert(program)
        src.uploadProgram(id)
        return id
    }
    
    suspend fun delete(id: Long) {
        dao.delete(id)
        src.deleteProgram(id)
    }
}