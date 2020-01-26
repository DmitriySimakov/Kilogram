package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.Program
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramDao {
    
    @Query("SELECT * FROM Program ORDER BY id DESC")
    fun programsFlow() : Flow<List<Program>>
    
    @Query("SELECT * FROM Program WHERE id = :id")
    suspend fun program(id: Long) : Program
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programs: List<Program>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(program: Program) : Long
    
    
    @Query("DELETE FROM Program WHERE id = :id")
    suspend fun delete(id: Long)
}