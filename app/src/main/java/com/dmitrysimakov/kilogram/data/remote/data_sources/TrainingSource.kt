package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseListWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingSetWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingWorker

class TrainingSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    fun uploadTraining(id: Long) { upload(id, UploadTrainingWorker::class.java) }
    
    fun uploadTrainingExercise(id: Long) { upload(id, UploadTrainingExerciseWorker::class.java) }
    
    fun uploadTrainingExerciseList(trainingId: Long) { upload(trainingId, UploadTrainingExerciseListWorker::class.java) }
    
    fun uploadTrainingSet(id: Long) { upload(id, UploadTrainingSetWorker::class.java) }
}