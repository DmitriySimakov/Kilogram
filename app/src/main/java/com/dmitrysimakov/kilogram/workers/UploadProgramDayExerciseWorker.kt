package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDayExercise
import com.dmitrysimakov.kilogram.util.programDayExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayExerciseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: ProgramDayExerciseDao by inject()
    
    override suspend fun doWork(): Result {
        val programDayExerciseId = inputData.getLong("id", 0)
        val (_, programDayId, exercise, indexNumber, rest, strategy) = dao.programDayExercise(programDayExerciseId)
        
        val programDayExercise = ProgramDayExercise(programDayExerciseId, programDayId, exercise, indexNumber, rest, strategy)
        programDayExercisesCollection.document(programDayExerciseId.toString()).set(programDayExercise)
        
        return Result.success()
    }
}