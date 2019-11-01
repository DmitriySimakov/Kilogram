package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.entity.Program

class ProgramRepository(private val programDao: ProgramDao) {
    
    fun loadProgramList() = programDao.getProgramList()
    
    suspend fun insertProgram(program: Program, callback: ((Long) -> Unit)? = null) {
        val id = programDao.insert(program)
        callback?.invoke(id)
    }
    
    suspend fun deleteProgram(id: Long) {
        programDao.delete(id)
    }
}