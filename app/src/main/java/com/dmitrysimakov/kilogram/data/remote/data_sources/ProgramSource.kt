package com.dmitrysimakov.kilogram.data.remote.data_sources

import android.content.Context
import com.dmitrysimakov.kilogram.workers.*

class ProgramSource(context: Context) : RemoteDataSource(context) {
    
    fun uploadProgram(id: Long) { upload(id, UploadProgramWorker::class.java) }
    
    fun uploadProgramDay(id: Long) { upload(id, UploadProgramDayWorker::class.java) }
    
    fun uploadProgramDayList(programId: Long) { upload(programId, UploadProgramDayListWorker::class.java) }
    
    fun uploadProgramDayExercise(id: Long) { upload(id, UploadProgramDayExerciseWorker::class.java) }
    
    fun uploadProgramDayExerciseList(programDayId: Long) { upload(programDayId, UploadProgramDayExerciseListWorker::class.java) }
}