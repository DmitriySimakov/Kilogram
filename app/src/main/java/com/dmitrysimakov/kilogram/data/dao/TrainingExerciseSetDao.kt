package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet

@Dao
interface TrainingExerciseSetDao {

    @Query("SELECT * FROM training_exercise_set WHERE training_exercise_id = :training_exercise_id")
    fun getSets(training_exercise_id: Long) : LiveData<List<TrainingExerciseSet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<TrainingExerciseSet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: TrainingExerciseSet)

    @Delete
    fun delete(set: TrainingExerciseSet)
}