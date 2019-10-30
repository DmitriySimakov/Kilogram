package com.dmitrysimakov.kilogram.data.local.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "training_exercise_set",
        indices = [Index(value = ["training_exercise_id"])],
        foreignKeys = [
            ForeignKey(
                    entity = TrainingExercise::class,
                    parentColumns = ["_id"],
                    childColumns = ["training_exercise_id"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
class TrainingExerciseSet(
        @PrimaryKey(autoGenerate = true) val _id: Long = 0,
        val training_exercise_id: Long,
        weight: Int? = null,
        reps: Int? = null,
        time: Int? = null,
        distance: Int? = null,
        var secs_since_start: Int? = null
) : BaseObservable() {
    
    @get:Bindable var weight = weight
        set(value) {
            field = value?.coerceIn(0, 10000)
            notifyPropertyChanged(BR.weight)
        }
    
    @get:Bindable var reps = reps
        set(value) {
            field = value?.coerceIn(0, 1000)
            notifyPropertyChanged(BR.reps)
        }
    
    @get:Bindable var time = time
        set(value) {
            field = value?.coerceIn(0, 24*60*60)
            notifyPropertyChanged(BR.time)
        }
    
    @get:Bindable var distance = distance
        set(value) {
            field = value?.coerceIn(0, 100000)
            notifyPropertyChanged(BR.distance)
        }
}