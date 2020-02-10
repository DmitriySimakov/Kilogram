package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.data.model.Training
import com.dmitrysimakov.kilogram.data.model.TrainingExercise
import com.dmitrysimakov.kilogram.data.model.TrainingSet
import com.dmitrysimakov.kilogram.data.remote.firestore
import com.dmitrysimakov.kilogram.data.remote.uid

class TrainingSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    private val trainingsRef
        get() = firestore.collection("users/$uid/trainings")
    private val trainingExercisesRef
        get() = firestore.collection("users/$uid/training_exercises")
    private val trainingSetsRef
        get() = firestore.collection("users/$uid/training_sets")
    
    suspend fun newTrainings(lastUpdate: Long) =
            getNewData(Training::class.java, trainingsRef, lastUpdate)
    
    suspend fun newTrainingExercises(lastUpdate: Long) =
            getNewData(TrainingExercise::class.java, trainingExercisesRef, lastUpdate)
    
    suspend fun newTrainingSets(lastUpdate: Long) =
            getNewData(TrainingSet::class.java, trainingSetsRef, lastUpdate)
    
    fun uploadTraining(training: Training) {
        trainingsRef.document(training.id).set(training)
    }
    
    fun uploadTrainingExercise(trainingExercise: TrainingExercise) {
        trainingExercisesRef.document(trainingExercise.id).set(trainingExercise)
    }
    
    fun uploadTrainingSet(trainingSet: TrainingSet) {
        trainingSetsRef.document(trainingSet.id).set(trainingSet)
    }
}