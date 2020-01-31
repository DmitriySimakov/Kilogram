package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.*
import com.dmitrysimakov.kilogram.util.firebaseUser

abstract class RemoteDataSource(private val workManager: WorkManager) {
    
    fun upload(id: Long, workerClass: Class<out ListenableWorker>) {
        val data = Data.Builder().putLong("id", id).build()
        upload(data, workerClass)
    }
    
    fun upload(data: Data, workerClass: Class<out ListenableWorker>) {
        if (firebaseUser == null) return
        
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()
        
        val request = OneTimeWorkRequest.Builder(workerClass)
                .setConstraints(constraints)
                .setInputData(data)
                .build()
        
        workManager.enqueue(request)
    }
}