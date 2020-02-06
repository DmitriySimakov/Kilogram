package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseListWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingSetWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingWorker

class TrainingSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    fun uploadTraining(id: String) { upload(id, UploadTrainingWorker::class.java) }
    fun deleteTraining(id: String) { delete(id, UploadTrainingWorker::class.java) }
    
    fun uploadTrainingExercise(id: String) { upload(id, UploadTrainingExerciseWorker::class.java) }
    fun deleteTrainingExercise(id: String) { delete(id, UploadTrainingExerciseWorker::class.java) }
    
    fun uploadTrainingExerciseList(trainingId: String) { upload(trainingId, UploadTrainingExerciseListWorker::class.java) }
    
    fun uploadTrainingSet(id: String) { upload(id, UploadTrainingSetWorker::class.java) }
    fun deleteTrainingSet(id: String) { delete(id, UploadTrainingSetWorker::class.java) }
}