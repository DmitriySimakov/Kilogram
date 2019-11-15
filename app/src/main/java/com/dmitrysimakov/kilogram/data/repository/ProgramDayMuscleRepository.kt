package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayMuscle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProgramDayMuscleRepository(
        private val dao: ProgramDayMuscleDao,
        private val io: CoroutineDispatcher = Dispatchers.IO
) {
    
    suspend fun loadParams(programId: Long) = withContext(io) {
        dao.getParamList(programId)
    }
    
    suspend fun insert(list: List<ProgramDayMuscle>) = withContext(io) {
        dao.insert(list)
    }
}