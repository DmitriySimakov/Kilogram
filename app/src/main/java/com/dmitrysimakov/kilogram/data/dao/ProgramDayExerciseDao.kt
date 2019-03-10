package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.relation.ProgramExerciseR

@Dao
interface ProgramDayExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<ProgramDayExercise>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(programDayExercise: ProgramDayExercise)
    
    @Query("""
        SELECT pde._id AS _id, e._id AS exercise_id, e.name, pde.num, pde.rest, pde.strategy
        FROM program_day_exercise AS pde
        LEFT JOIN exercise AS e
        ON pde.exercise_id = e._id
        WHERE pde.program_day_id = :programDayId
        ORDER BY pde.num
    """)
    fun getExercises(programDayId: Long) : LiveData<List<ProgramExerciseR>>
    
    @Query("DELETE FROM program_day_exercise WHERE _id = :id")
    fun delete(id: Long)
    
    @Query("UPDATE program_day_exercise SET num = :num WHERE _id = :id")
    fun setNum(id: Long, num: Int)
    
    @Transaction
    fun updateNums(exercises: List<ProgramExerciseR>) {
        for (exercise in exercises) {
            setNum(exercise._id, exercise.num)
        }
    }
}