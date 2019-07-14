package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayMuscle
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject

class ProgramDayMuscleRepository @Inject constructor(
        private val executors: AppExecutors,
        private val dao: ProgramDayMuscleDao
) {
    
    fun loadParams(programId: Long) = dao.getParams(programId)
    
    fun insert(list: List<ProgramDayMuscle>) {
        executors.diskIO().execute {
            dao.insert(list)
        }
    }
}