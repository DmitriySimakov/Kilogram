package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.data.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR

@Dao
interface TrainingExerciseDao {

    @Query("""
        SELECT te._id AS _id, e._id AS exercise_id, e.name
        FROM training_exercise AS te
        LEFT JOIN exercise AS e
        ON te.exercise_id = e._id
        WHERE te.training_id = :training_id
        ORDER BY te.num
    """)
    fun getExercises(training_id: Long): LiveData<List<TrainingExerciseR>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<TrainingExercise>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainingExercise: TrainingExercise)

    @Query("DELETE FROM training_exercise WHERE _id = :id")
    fun deleteExerciseFromTraining(id: Long)
    
    @Query("""
        SELECT
        measure_weight AS weight,
        measure_reps AS reps,
        measure_time AS time,
        measure_distance AS distance
        FROM training_exercise
        WHERE _id = :trainingExerciseId
        """)
    fun getExerciseMeasures(trainingExerciseId: Long): LiveData<ExerciseMeasures>
    
    @Suppress("AndroidUnresolvedRoomSqlReference")
    @Query("""
        INSERT INTO training_exercise (training_id, exercise_id, num, measure_weight, measure_reps, measure_time, measure_distance)
        SELECT :trainingId, exercise_id, num, measure_weight, measure_reps, measure_time, measure_distance
        FROM program_day_exercise
        WHERE program_day_id = :programDayId
    """)
    fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long)
}