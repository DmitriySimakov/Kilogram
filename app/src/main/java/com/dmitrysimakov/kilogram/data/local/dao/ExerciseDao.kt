package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import com.dmitrysimakov.kilogram.data.local.relation.ExerciseMeasures
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    
    @RawQuery(observedEntities = [Exercise::class])
    fun exercisesFlow(query: SupportSQLiteQuery) : Flow<List<Exercise>>
    
    @Query("""SELECT measure_weight AS weight, measure_reps AS reps, measure_time AS time, measure_distance AS distance
        FROM exercise WHERE name = :exerciseName""")
    suspend fun measures(exerciseName: String) : ExerciseMeasures
    
    @Query("SELECT * FROM exercise WHERE name = :name")
    suspend fun exercise(name: String) : Exercise
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercises: List<Exercise>)
    
    
    @Query("UPDATE exercise SET is_favorite = :isFavorite WHERE name = :name")
    suspend fun setFavorite(name: String, isFavorite: Boolean)
    
    @Query("UPDATE exercise SET executions_cnt = executions_cnt + 1 WHERE name = :name")
    suspend fun increaseExecutionsCnt(name: String)
    
    @Query("UPDATE exercise SET executions_cnt = executions_cnt - 1 WHERE name = :name")
    suspend fun decreaseExecutionsCnt(name: String)
}