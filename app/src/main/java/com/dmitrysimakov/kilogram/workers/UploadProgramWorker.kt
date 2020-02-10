package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: ProgramRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            val programDayExerciseId = inputData.getString(ID)!!
            val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
            var program = repo.program(programDayExerciseId)
            if (needToDelete) program = program.copy(deleted = true)
        
            repo.uploadProgram(program)
        
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}