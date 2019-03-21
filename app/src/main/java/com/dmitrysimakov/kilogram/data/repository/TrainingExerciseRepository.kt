package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingExerciseRepository @Inject constructor(
        private val executors: AppExecutors,
        private val trainingExerciseDao: TrainingExerciseDao
) {
    
    fun loadTrainingExerciseRs(trainingId: Long) = trainingExerciseDao.getTrainingExerciseRs(trainingId)
    
    fun loadTrainingExerciseR(id: Long) = trainingExerciseDao.getTrainingExerciseR(id)
    
    fun loadTrainingExercise(id: Long) = trainingExerciseDao.getTrainingExercise(id)
    
    fun loadExerciseMeasures(trainingExerciseId: Long) = trainingExerciseDao.getExerciseMeasures(trainingExerciseId)
    
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
    
    fun finishTrainingExercises(trainingId: Long) {
        executors.diskIO().execute {
            trainingExerciseDao.finishTrainingExercises(trainingId, TrainingExercise.FINISHED, TrainingExercise.RUNNING)
        }
    }
}