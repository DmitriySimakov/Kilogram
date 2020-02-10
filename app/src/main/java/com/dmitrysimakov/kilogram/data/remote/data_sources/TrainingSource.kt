package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.data.model.Training
import com.dmitrysimakov.kilogram.data.model.TrainingExercise
import com.dmitrysimakov.kilogram.data.model.TrainingSet
import com.dmitrysimakov.kilogram.data.remote.firestore
import com.dmitrysimakov.kilogram.data.remote.getNewData
import com.dmitrysimakov.kilogram.data.remote.uid
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseListWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingSetWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingWorker

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
    
    fun uploadTraining(id: String) { upload(id, UploadTrainingWorker::class.java) }
    fun deleteTraining(id: String) { delete(id, UploadTrainingWorker::class.java) }
    
    fun uploadTrainingExercise(id: String) { upload(id, UploadTrainingExerciseWorker::class.java) }
    fun deleteTrainingExercise(id: String) { delete(id, UploadTrainingExerciseWorker::class.java) }
    
    fun uploadTrainingExerciseList(trainingId: String) { upload(trainingId, UploadTrainingExerciseListWorker::class.java) }
    
    fun uploadTrainingSet(id: String) { upload(id, UploadTrainingSetWorker::class.java) }
    fun deleteTrainingSet(id: String) { delete(id, UploadTrainingSetWorker::class.java) }
}