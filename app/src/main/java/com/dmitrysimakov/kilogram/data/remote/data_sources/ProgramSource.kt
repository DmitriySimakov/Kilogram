package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.model.ProgramDay
import com.dmitrysimakov.kilogram.data.model.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.remote.firestore
import com.dmitrysimakov.kilogram.data.remote.uid
import com.dmitrysimakov.kilogram.util.live_data.liveData
import kotlinx.coroutines.tasks.await

class ProgramSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    private val programsRef
        get() = firestore.collection("users/$uid/programs")
    private val programDaysRef
        get() = firestore.collection("users/$uid/program_days")
    private val programDayExercisesRef
        get() = firestore.collection("users/$uid/program_day_exercises")
    
    fun programs() = firestore.collection("programs").liveData {
        it.toObject(Program::class.java)!!
    }
    
    fun programs(limit: Long) =
            firestore.collection("programs").limit(limit).liveData {
                it.toObject(Program::class.java)!!
            }
    
    suspend fun program(id: String) =
            firestore.document("programs/$id").get().await().toObject(Program::class.java)!!
    
    suspend fun programDays(programId: String): List<ProgramDay> =
            firestore.collection("programs/$programId/program_days")
                    .get().await().toObjects(ProgramDay::class.java)
    
    suspend fun programDay(programId: String, programDayId: String) =
            firestore.document("programs/$programId/program_days/$programDayId")
                    .get().await().toObject(ProgramDay::class.java)!!
    
    suspend fun programDayExercises(programId: String, programDayId: String): List<ProgramDayExercise> =
            firestore.collection("programs/$programId/program_days/$programDayId/program_day_exercises")
                    .get().await().toObjects(ProgramDayExercise::class.java)
    
    suspend fun newPrograms(lastUpdate: Long) =
            getNewData(Program::class.java, programsRef, lastUpdate)
    
    suspend fun newProgramDays(lastUpdate: Long) =
            getNewData(ProgramDay::class.java, programDaysRef, lastUpdate)
    
    suspend fun newProgramDayExercises(lastUpdate: Long) =
            getNewData(ProgramDayExercise::class.java, programDayExercisesRef, lastUpdate)
    
    fun uploadProgram(program: Program) {
        programsRef.document(program.id).set(program)
    }
    
    fun uploadProgramDay(programDay: ProgramDay) {
        programDaysRef.document(programDay.id).set(programDay)
    }
    
    fun uploadProgramDayExercise(programDayExercise: ProgramDayExercise) {
        programDayExercisesRef.document(programDayExercise.id).set(programDayExercise)
    }
    
    
    fun publishProgram(program: Program) {
        firestore.document("programs/${program.id}").set(program)
    }
    
    fun publishProgramDay(programId: String, programDay: ProgramDay) {
        firestore.document("programs/$programId/program_days/${programDay.id}").set(programDay)
    }
    
    fun publishProgramDayExercise(programId: String, programDayId: String, programDayExercise: ProgramDayExercise) {
        firestore.document("programs/$programId/program_days/$programDayId/program_day_exercises/${programDayExercise.id}").set(programDayExercise)
    }
}