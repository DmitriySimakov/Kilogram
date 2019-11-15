package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.entity.Program
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProgramRepository(
        private val programDao: ProgramDao,
        private val io: CoroutineDispatcher = Dispatchers.IO
) {
    
    suspend fun loadProgramList() = withContext(io) {
        programDao.getProgramList()
    }
    
    suspend fun insertProgram(program: Program) = withContext(io) {
        programDao.insert(program)
    }
    
    suspend fun deleteProgram(id: Long) = withContext(io) {
        programDao.delete(id)
    }
}