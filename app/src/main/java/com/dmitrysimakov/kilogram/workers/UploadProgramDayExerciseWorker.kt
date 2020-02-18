package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class UploadProgramDayExerciseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: ProgramDayExerciseRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            val id = inputData.getString(ID)!!
            val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
            
            repo.uploadProgramDayExercise(repo.programDayExercise(id).copy(deleted = needToDelete, lastUpdate = Date()))
            
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}