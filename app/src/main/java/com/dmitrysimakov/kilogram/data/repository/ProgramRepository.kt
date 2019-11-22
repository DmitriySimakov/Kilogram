package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.entity.Program

class ProgramRepository(private val dao: ProgramDao) {
    
    fun programsFlow() = dao.programsFlow()
    
    suspend fun insert(program: Program) = dao.insert(program)
    
    suspend fun delete(id: Long) = dao.delete(id)
}