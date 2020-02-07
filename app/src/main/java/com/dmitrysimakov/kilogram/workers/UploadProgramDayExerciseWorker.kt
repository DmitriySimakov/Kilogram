package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.model.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.remote.userProgramDayExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayExerciseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: ProgramDayExerciseDao by inject()
    
    override suspend fun doWork(): Result {
        val programDayExerciseId = inputData.getString(ID)!!
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val programDayExercise = if (needToDelete) ProgramDayExercise(id = programDayExerciseId, deleted = true)
        else dao.programDayExercise(programDayExerciseId)
        
        userProgramDayExercisesCollection.document(programDayExerciseId.toString()).set(programDayExercise)
        
        return Result.success()
    }
}