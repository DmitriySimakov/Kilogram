package com.dmitrysimakov.kilogram.data.remote.data_sources

import android.content.Context
import androidx.work.*
import com.dmitrysimakov.kilogram.util.firebaseUser
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingSetWorker
import com.dmitrysimakov.kilogram.workers.UploadTrainingWorker

class TrainingSource(private val context: Context) {
    
    fun uploadTraining(id: Long) { upload(id, UploadTrainingWorker::class.java) }
    
    fun uploadTrainingExercise(id: Long) { upload(id, UploadTrainingExerciseWorker::class.java) }
    
    fun uploadSet(id: Long) { upload(id, UploadTrainingSetWorker::class.java) }
    
    private fun upload(id: Long, workerClass: Class<out ListenableWorker>) {
        if (firebaseUser == null) return
        
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()
        
        val data = Data.Builder().putLong("id", id).build()
    
        val request = OneTimeWorkRequest.Builder(workerClass)
                .setConstraints(constraints)
                .setInputData(data)
                .build()
    
        WorkManager.getInstance(context).enqueue(request)
    }
}