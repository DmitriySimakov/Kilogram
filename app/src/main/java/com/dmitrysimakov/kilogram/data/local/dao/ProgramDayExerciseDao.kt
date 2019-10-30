package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise

@Dao
interface ProgramDayExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(programDayExercises: List<ProgramDayExercise>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(programDayExercise: ProgramDayExercise)
    
    @Query("""
        SELECT * FROM program_day_exercise
        WHERE program_day_id = :programDayId
        ORDER BY indexNumber
    """)
    fun getProgramDayExerciseList(programDayId: Long) : LiveData<List<ProgramDayExercise>>
    
    @Update
    fun update(programDayExercises: List<ProgramDayExercise>)
    
    @Delete
    fun delete(programDayExercise: ProgramDayExercise)
}