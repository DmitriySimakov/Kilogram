package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.remote.models.Program
import com.dmitrysimakov.kilogram.util.programsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: ProgramDao by inject()
    
    override suspend fun doWork(): Result {
        val programId = inputData.getLong(ID, 0)
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val program = if (needToDelete) {
            Program(programId, deleted = true)
        } else {
            val (_, name, description) = dao.program(programId)
            Program(programId, name, description)
        }
        
        programsCollection.document(programId.toString()).set(program)
        
        return Result.success()
    }
}