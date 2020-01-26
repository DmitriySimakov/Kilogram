package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.models.Program
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.util.programsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repository: ProgramRepository by inject()
    
    override suspend fun doWork(): Result {
        val programId = inputData.getLong("id", 0)
        val (id, name, description) = repository.program(programId)
        
        val program = Program(id, name, description)
        programsCollection.document(id.toString()).set(program)
        
        return Result.success()
    }
}