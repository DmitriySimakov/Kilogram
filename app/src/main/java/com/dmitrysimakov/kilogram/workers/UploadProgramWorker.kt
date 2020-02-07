package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.remote.userProgramsCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: ProgramDao by inject()
    
    override suspend fun doWork(): Result {
        val programId = inputData.getString(ID)!!
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val program = if (needToDelete) Program(programId, deleted = true)
        else dao.program(programId)
        
        userProgramsCollection.document(programId).set(program)
        
        return Result.success()
    }
}