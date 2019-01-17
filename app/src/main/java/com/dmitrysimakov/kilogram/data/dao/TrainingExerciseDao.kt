package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.data.entity.TrainingExercise

@Dao
interface TrainingExerciseDao {

    @Query("""
        SELECT e._id, e.name, e.main_muscle_id, e.mechanics_type_id, e.exercise_type_id, e.equipment_id, e.description
        FROM training_exercise AS te
        LEFT JOIN exercise AS e
        ON te.exercise_id = e._id
        WHERE te.training_id = :training_id
        ORDER BY te.number
    """)
    fun getExercises(training_id: Long) : LiveData<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<TrainingExercise>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainingExercise: TrainingExercise)

    @Query("DELETE FROM training_exercise WHERE training_id = :training_id AND exercise_id = :exercise_id")
    fun deleteExerciseFromTraining(exercise_id: Long, training_id: Long)
}