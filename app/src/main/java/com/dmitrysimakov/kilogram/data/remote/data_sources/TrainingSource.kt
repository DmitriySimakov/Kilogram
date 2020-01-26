package com.dmitrysimakov.kilogram.data.remote.data_sources

import android.content.Context
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseListWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingSetWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingWorker

class TrainingSource(private val context: Context) {
    
    fun uploadTraining(id: Long) { upload(context, id, UploadTrainingWorker::class.java) }
    
    fun uploadTrainingExercise(id: Long) { upload(context, id, UploadTrainingExerciseWorker::class.java) }
    
    fun uploadTrainingExerciseList(trainingId: Long) { upload(context, trainingId, UploadTrainingExerciseListWorker::class.java) }
    
    fun uploadTrainingSet(id: Long) { upload(context, id, UploadTrainingSetWorker::class.java) }
}