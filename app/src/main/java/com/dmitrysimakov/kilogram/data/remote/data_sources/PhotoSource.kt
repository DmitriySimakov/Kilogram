package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.workers.UploadPhotoWorker

class PhotoSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    fun uploadPhoto(uri: String) { upload(uri, UploadPhotoWorker::class.java) }
    fun deletePhoto(uri: String) { delete(uri, UploadPhotoWorker::class.java) }
}