package com.dmitrysimakov.kilogram.ui.trainings.sets

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.SetWithPreviousResults
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseSetRepository
import com.dmitrysimakov.kilogram.util.setNewValue
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
    
    val exercise = Transformations.switchMap(_trainingExerciseId) {
        trainingExerciseRepository.loadTrainingExercise(it)
    }
    
    val prevExercise = Transformations.switchMap(exercise) {
        trainingExerciseRepository.loadPrevTrainingExercise(trainingId, it.exercise)
    }
    
    val currSets = Transformations.switchMap(exercise) {
        trainingExerciseSetRepository.loadSets(it._id)
    }
    
    val prevSets = Transformations.switchMap(prevExercise) {
        if (it != null) trainingExerciseSetRepository.loadSets(it.training_exercise_id)
        else MutableLiveData(emptyList())
    }
    
    val sets = MediatorLiveData<List<SetWithPreviousResults>>()
    
    init {
        fun joinSets() {
            val res = mutableListOf<SetWithPreviousResults>()
            for (i in 0 until max(currSets.value?.size ?: 0, prevSets.value?.size ?: 0)) {
                val curr = try { currSets.value?.get(i) } catch (e: Exception) {null}
                val prev = try {prevSets.value?.get(i) } catch (e: Exception) {null}
                res.add(SetWithPreviousResults(curr?._id ?: 0L, curr?.weight, curr?.reps, curr?.time, curr?.distance,
                        prev?._id ?: 0L, prev?.weight, prev?.reps, prev?.time, prev?.distance))
            }
            sets.value = res
        }
        
        sets.addSource(currSets) { joinSets() }
        sets.addSource(prevSets) { joinSets() }
    }

    fun deleteSet(id: Long) { viewModelScope.launch {
        trainingExerciseSetRepository.deleteSet(id)
    }}
    
    fun finishExercise(trainingExerciseId: Long) { viewModelScope.launch {
        trainingExerciseRepository.updateState(trainingExerciseId, TrainingExercise.FINISHED)
        exercise.value?.let { exerciseRepository.increaseExecutionsCnt(it.exercise) }
    }}
}