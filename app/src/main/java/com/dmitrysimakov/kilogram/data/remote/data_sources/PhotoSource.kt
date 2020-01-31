package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.Data
import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.workers.UploadPhotoWorker

const val PHOTO_URI = "uri"

class PhotoSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    fun uploadPhoto(uri: String) {
        val data = Data.Builder().putString("uri", uri).build()
        upload(data, UploadPhotoWorker::class.java)
    }
    
    fun deletePhoto(uri: String) {
        val data = Data.Builder()
                .putString(PHOTO_URI, uri)
                .putBoolean(NEED_TO_DELETE, true)
                .build()
        upload(data, UploadPhotoWorker::class.java)
    }
}