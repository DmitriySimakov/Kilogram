package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.Data
import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.workers.UploadPhotoWorker

class PhotoSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    fun uploadPhoto(uri: String) {
        val data = Data.Builder().putString("uri", uri).build()
        upload(data, UploadPhotoWorker::class.java)
    }
    
    fun deletePhoto(uri: String) {
        val data = Data.Builder()
                .putString("uri", uri)
                .putBoolean("delete", true)
                .build()
        upload(data, UploadPhotoWorker::class.java)
    }
}