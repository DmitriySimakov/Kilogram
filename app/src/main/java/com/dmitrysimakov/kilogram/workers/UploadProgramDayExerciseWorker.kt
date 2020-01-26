package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.util.programDayExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayExerciseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repository: ProgramDayExerciseRepository by inject()
    
    override suspend fun doWork(): Result {
        val programDayExerciseId = inputData.getLong("id", 0)
        val (id, programDayId, exercise, indexNumber, rest, strategy) = repository.programDayExercise(programDayExerciseId)
        
        val programDayExercise = ProgramDayExercise(id, programDayId, exercise, indexNumber, rest, strategy)
        programDayExercisesCollection.document(id.toString()).set(programDayExercise)
        
        return Result.success()
    }
}