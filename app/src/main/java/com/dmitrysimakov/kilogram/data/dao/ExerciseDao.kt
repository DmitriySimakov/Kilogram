package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.data.relation.ExerciseR

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Exercise>)

    @Query("SELECT * FROM exercise")
    fun getExerciseList() : LiveData<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE main_muscle_id = :muscleId")
    fun getExerciseList(muscleId: Long) : LiveData<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE _id = :id")
    fun getExercise(id: Long) : LiveData<Exercise>

    @Query("""
        SELECT name, description,
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
    fun getExerciseR(id: Long) : LiveData<ExerciseR>
}