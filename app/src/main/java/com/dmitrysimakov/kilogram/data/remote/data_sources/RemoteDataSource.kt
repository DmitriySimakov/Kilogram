package com.dmitrysimakov.kilogram.data.remote.data_sources

import android.content.Context
import androidx.work.*
import com.dmitrysimakov.kilogram.util.firebaseUser

fun upload(context: Context, id: Long, workerClass: Class<out ListenableWorker>) {
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
