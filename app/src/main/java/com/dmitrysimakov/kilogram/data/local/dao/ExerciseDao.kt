package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import com.dmitrysimakov.kilogram.data.relation.DetailedExerciseR

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    fun getExerciseList() : LiveData<List<Exercise>>
    
    @RawQuery(observedEntities = [Exercise::class])
    fun getExerciseList(query: SupportSQLiteQuery) : LiveData<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE main_muscle_id = :muscleId")
    fun getExerciseList(muscleId: Long) : LiveData<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE _id = :id")
    fun getExercise(id: Long) : LiveData<Exercise>

    @Query("""
        SELECT _id, name, description, is_favorite,
        (SELECT name FROM muscle WHERE _id = main_muscle_id ) AS main_muscle,
        (SELECT group_concat(m.name, ', ')
            FROM  targeted_muscle  AS tm LEFT JOIN muscle AS m
            ON tm.muscle_id  = m._id
            WHERE tm.exercise_id = :id) AS targeted_muscles,
        (SELECT name FROM mechanics_type WHERE _id = ex.mechanics_type_id) AS mechanics_type,
        (SELECT name FROM exercise_type WHERE _id = ex.exercise_type_id) AS exercise_type,
        (SELECT name FROM equipment WHERE _id = ex.equipment_id) AS equipment
        FROM exercise AS ex
        WHERE _id = :id
    """)
    fun getDetailedExerciseR(id: Long) : LiveData<DetailedExerciseR>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Exercise>)
    
    @Update
    fun updateExercise(exercise: Exercise)
    
    @Query("UPDATE exercise SET is_favorite = :isFavorite WHERE _id = :id")
    fun setFavorite(id: Long, isFavorite: Boolean)
    
    @Query("UPDATE exercise SET executions_cnt = executions_cnt + 1 WHERE _id = :id")
    fun increaseExecutionsCnt(id: Long)
    
    @Query("UPDATE exercise SET executions_cnt = executions_cnt - 1 WHERE _id = :id")
    fun decreaseExecutionsCnt(id: Long)
}