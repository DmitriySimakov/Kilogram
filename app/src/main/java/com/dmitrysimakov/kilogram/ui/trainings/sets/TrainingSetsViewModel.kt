package com.dmitrysimakov.kilogram.ui.trainings.sets

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.SetWithPreviousResults
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseSetRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.max

class TrainingSetsViewModel(
        private val trainingExerciseSetRepository: TrainingExerciseSetRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    var trainingId: Long = 0
    
    private val _trainingExerciseId = MutableLiveData<Long>()
    fun setTrainingExercise(id: Long) {
        _trainingExerciseId.setNewValue(id)
    }
    
    val exercise = _trainingExerciseId.switchMap {
        trainingExerciseRepository.loadTrainingExercise(it)
    }
    
    val prevExercise = exercise.switchMap {
        trainingExerciseRepository.loadPrevTrainingExercise(trainingId, it.exercise)
    }
    
    val currentSets = exercise.switchMap {
        trainingExerciseSetRepository.loadSets(it._id)
    }
    
    val previousSets = prevExercise.switchMap {
        if (it != null) trainingExerciseSetRepository.loadSets(it.training_exercise_id)
        else MutableLiveData(emptyList())
    }
    
    val sets = MediatorLiveData<List<SetWithPreviousResults>>()
    
    init {
        fun joinSets() {
            val res = mutableListOf<SetWithPreviousResults>()
            val currSets = currentSets.value;
            val prevSets = previousSets.value
            if (currSets != null && prevSets != null) {
                for (i in 0 until max(currSets.size, prevSets.size)) {
                    val curr = try { currSets[i] } catch (e: Exception) { null }
                    val prev = try { prevSets[i] } catch (e: Exception) { null }
                    val set = SetWithPreviousResults(
                            curr?._id ?: 0,
                            curr?.weight,
                            curr?.reps,
                            curr?.time,
                            curr?.distance,
                            prev?._id ?: 0,
                            prev?.weight,
                            prev?.reps,
                            prev?.time,
                            prev?.distance
                    )
                    res.add(set)
                }
            }
            sets.value = res
        }
        
        sets.addSource(currentSets) { joinSets() }
        sets.addSource(previousSets) { joinSets() }
    }

    fun deleteSet(id: Long) { CoroutineScope(Dispatchers.IO).launch {
        trainingExerciseSetRepository.deleteSet(id)
    }}
    
    fun finishExercise(trainingExerciseId: Long) { CoroutineScope(Dispatchers.IO).launch {
        trainingExerciseRepository.updateState(trainingExerciseId, TrainingExercise.FINISHED)
        exercise.value?.let { exerciseRepository.increaseExecutionsCnt(it.exercise) }
    }}
}