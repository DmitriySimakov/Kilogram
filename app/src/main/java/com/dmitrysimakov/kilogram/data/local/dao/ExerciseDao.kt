package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.entity.Exercise

@Dao
interface ExerciseDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Exercise>)
    
    @Query("SELECT * FROM exercise")
    fun getExerciseList() : LiveData<List<Exercise>>
    
    @RawQuery(observedEntities = [Exercise::class])
    fun getExerciseList(query: SupportSQLiteQuery) : LiveData<List<Exercise>>
    
    @Query("SELECT * FROM exercise WHERE name = :name")
    fun getExercise(name: String) : LiveData<Exercise>
    
    @Update
    fun update(exercise: Exercise)
    
    @Query("UPDATE exercise SET is_favorite = :isFavorite WHERE name = :name")
    fun setFavorite(name: String, isFavorite: Boolean)
    
    @Query("UPDATE exercise SET executions_cnt = executions_cnt + 1 WHERE name = :name")
    fun increaseExecutionsCnt(name: String)
    
    @Query("UPDATE exercise SET executions_cnt = executions_cnt - 1 WHERE name = :name")
    fun decreaseExecutionsCnt(name: String)
}