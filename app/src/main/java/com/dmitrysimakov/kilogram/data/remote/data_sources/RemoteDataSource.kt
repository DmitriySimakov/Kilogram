package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.*
import com.dmitrysimakov.kilogram.util.firebaseUser

const val ID = "id"
const val NEED_TO_DELETE = "need_to_delete"

abstract class RemoteDataSource(private val workManager: WorkManager) {
    
    fun upload(id: String, workerClass: Class<out ListenableWorker>) {
        val data = Data.Builder().putString(ID, id).build()
        upload(data, workerClass)
    }
    
    fun delete(id: String, workerClass: Class<out ListenableWorker>) {
        val data = Data.Builder()
                .putString(ID, id)
                .putBoolean(NEED_TO_DELETE, true)
                .build()
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