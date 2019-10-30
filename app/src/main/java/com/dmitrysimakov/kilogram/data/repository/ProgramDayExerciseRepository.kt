package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.util.AppExecutors

class ProgramDayExerciseRepository(
        private val executors: AppExecutors,
        private val programDayExerciseDao: ProgramDayExerciseDao
) {
   
    fun loadProgramDayExerciseList(programDayId: Long) =
            programDayExerciseDao.getProgramDayExerciseList(programDayId)
    
    fun addExerciseToProgramDay(exercise: ProgramDayExercise) {
        executors.diskIO().execute {
            programDayExerciseDao.insert(exercise)
        }
    }
    
    fun deleteExerciseFromProgramDay(exercise: ProgramDayExercise) {
        executors.diskIO().execute {
            programDayExerciseDao.delete(exercise)
        }
    }
    
    fun update(list: List<ProgramDayExercise>) {
        executors.diskIO().execute {
            programDayExerciseDao.update(list)
        }
    }
}