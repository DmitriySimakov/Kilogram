package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayListWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: ProgramDayRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            val programId = inputData.getString(ID)!!
            repo.uploadProgramDays(repo.programDays(programId))
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}