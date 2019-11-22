package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayTargetDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayTarget

class ProgramDayTargetsRepository(private val dao: ProgramDayTargetDao) {
    
    suspend fun params(programId: Long) = dao.params(programId)
    
    suspend fun insert(programDayTargets: List<ProgramDayTarget>) = dao.insert(programDayTargets)
}