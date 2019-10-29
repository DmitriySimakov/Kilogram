package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.util.AppExecutors

class TrainingExerciseRepository(
        private val executors: AppExecutors,
        private val trainingExerciseDao: TrainingExerciseDao
) {
    
    fun loadTrainingExerciseRs(trainingId: Long) = trainingExerciseDao.getTrainingExerciseRs(trainingId)
    
    fun loadTrainingExerciseR(id: Long) = trainingExerciseDao.getTrainingExerciseR(id)
    
    fun loadPrevTrainingExerciseInfo(trainingId: Long, exerciseId: Long)
            = trainingExerciseDao.getPrevTrainingExerciseInfo(trainingId, exerciseId)
    
    fun loadTrainingExercise(id: Long) = trainingExerciseDao.getTrainingExercise(id)
    
    fun addExercise(exercise: TrainingExercise) {
        executors.diskIO().execute{
            trainingExerciseDao.insert(exercise)
        }
    }

    fun deleteExercise(exercise: TrainingExerciseR) {
        executors.diskIO().execute{
            trainingExerciseDao.deleteExerciseFromTraining(exercise._id)
        }
    }
    
    fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long) {
        executors.diskIO().execute {
            trainingExerciseDao.fillTrainingWithProgramExercises(trainingId, programDayId)
        }
    }
    
    fun updateState(id: Long, state: Int) {
        executors.diskIO().execute {
            trainingExerciseDao.updateState(id, state)
        }
    }
    
    fun updateNums(items: List<TrainingExerciseR>) {
        executors.diskIO().execute {
            trainingExerciseDao.updateNums(items)
        }
    }
    
    fun finishTrainingExercises(trainingId: Long) {
        executors.diskIO().execute {
            trainingExerciseDao.finishTrainingExercises(trainingId, TrainingExercise.FINISHED, TrainingExercise.RUNNING)
        }
    }
}