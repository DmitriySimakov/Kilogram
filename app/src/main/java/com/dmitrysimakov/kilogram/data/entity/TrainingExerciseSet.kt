package com.dmitrysimakov.kilogram.data.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.BR
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "training_exercise_set",
        foreignKeys = [
            ForeignKey(
                    entity = TrainingExercise::class,
                    parentColumns = ["_id"],
                    childColumns = ["training_exercise_id"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
class TrainingExerciseSet(
        @PrimaryKey(autoGenerate = true) override val _id: Long = 0,
        val training_exercise_id: Long,
        val secs_since_start: Int? = null,
        weight: Int? = null,
        reps: Int? = null,
        time: Int? = null,
        distance: Int? = null
) : BaseObservable(), HasId {
    
    @get:Bindable var weight = weight
        set(value) {
            field = value
            notifyPropertyChanged(BR.weight)
        }
    
    @get:Bindable var reps = reps
        set(value) {
            field = value
            notifyPropertyChanged(BR.reps)
        }
    
    @get:Bindable var time = time
        set(value) {
            field = value
            notifyPropertyChanged(BR.time)
        }
    
    @get:Bindable var distance = distance
        set(value) {
            field = value
            notifyPropertyChanged(BR.distance)
        }
}