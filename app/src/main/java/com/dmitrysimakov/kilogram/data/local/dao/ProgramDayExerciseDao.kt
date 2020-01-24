package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramDayExerciseDao {
    
    @Query("""
        SELECT * FROM ProgramDayExercise
        WHERE programDayId = :programDayId
        ORDER BY indexNumber
    """)
    fun programDayExercisesFlow(programDayId: Long) : Flow<List<ProgramDayExercise>>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDayExercises: List<ProgramDayExercise>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programDayExercise: ProgramDayExercise)
    
    
    @Update
    suspend fun update(programDayExercises: List<ProgramDayExercise>)
    
    
    @Query("DELETE FROM ProgramDayExercise WHERE id = :id")
    suspend fun delete(id: Long)
}