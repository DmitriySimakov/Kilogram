package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.entity.Program

class ProgramRepository(private val programDao: ProgramDao) {
    
    suspend fun loadProgramList() =
            programDao.getProgramList()
    
    suspend fun insertProgram(program: Program) {
        programDao.insert(program)
    }
    
    suspend fun deleteProgram(id: Long) {
        programDao.delete(id)
    }
}