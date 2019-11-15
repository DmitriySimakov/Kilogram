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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Muscle>)

    @Query("SELECT * FROM muscle")
    suspend fun getMuscleList() : List<Muscle>
    
    @Query("SELECT name, 0 AS is_active FROM muscle")
    suspend fun getParamList() : List<FilterParam>
    
    @Query("""SELECT m.name,
        CASE WHEN pdm.program_day_id IS NULL THEN 0 ELSE 1 END AS is_active
        FROM muscle AS m
        LEFT JOIN program_day_muscle AS pdm
        ON m.name = pdm.muscle AND pdm.program_day_id = :programDayId
    """)
    suspend fun getProgramDayParamList(programDayId: Long) : List<FilterParam>
}