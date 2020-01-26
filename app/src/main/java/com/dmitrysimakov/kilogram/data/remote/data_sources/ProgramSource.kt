package com.dmitrysimakov.kilogram.data.remote.data_sources

import android.content.Context
import com.dmitrysimakov.kilogram.workers.*

class ProgramSource(private val context: Context) {
    
    fun uploadProgram(id: Long) { upload(context, id, UploadProgramWorker::class.java) }
    
    fun uploadProgramDay(id: Long) { upload(context, id, UploadProgramDayWorker::class.java) }
    
    fun uploadProgramDayList(programId: Long) { upload(context, programId, UploadProgramDayListWorker::class.java) }
    
    fun uploadProgramDayExercise(id: Long) { upload(context, id, UploadProgramDayExerciseWorker::class.java) }
    
    fun uploadProgramDayExerciseList(programDayId: Long) { upload(context, programDayId, UploadProgramDayExerciseListWorker::class.java) }
}