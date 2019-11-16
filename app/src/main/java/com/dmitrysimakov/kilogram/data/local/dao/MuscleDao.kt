package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.Muscle
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface MuscleDao {

    @Query("SELECT * FROM muscle")
    suspend fun muscles() : List<Muscle>
    
    @Query("SELECT name, 0 AS is_active FROM muscle")
    suspend fun params() : List<FilterParam>
    
    @Query("""SELECT m.name,
        CASE WHEN pdm.program_day_id IS NULL THEN 0 ELSE 1 END AS is_active
        FROM muscle AS m
        LEFT JOIN program_day_muscle AS pdm
        ON m.name = pdm.muscle AND pdm.program_day_id = :programDayId
    """)
    suspend fun programDayParams(programDayId: Long) : List<FilterParam>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(muscles: List<Muscle>)
}