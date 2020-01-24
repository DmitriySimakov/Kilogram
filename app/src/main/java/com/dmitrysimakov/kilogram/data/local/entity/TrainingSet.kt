package com.dmitrysimakov.kilogram.data.local.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(indices = [Index(value = ["trainingExerciseId"])],
        foreignKeys = [
            ForeignKey(
                    entity = TrainingExercise::class,
                    parentColumns = ["id"],
                    childColumns = ["trainingExerciseId"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
class TrainingSet(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val trainingExerciseId: Long,
        weight: Int? = null,
        reps: Int? = null,
        time: Int? = null,
        distance: Int? = null,
        var dateTime: OffsetDateTime? = null
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