package com.dmitrysimakov.kilogram.ui.home.trainings.training_sets

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.relation.SetWithPreviousResults
import com.dmitrysimakov.kilogram.data.model.TrainingExercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingSetRepository
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.launch
import kotlin.math.max

class TrainingSetsViewModel(
        private val trainingSetRepository: TrainingSetRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    
    val trainingExerciseId = MutableLiveData<String>()
    
    val trainingExercise = trainingExerciseId.switchMap {
        trainingExerciseRepository.trainingExerciseFlow(it).asLiveData()
    }
    
    val exercise = trainingExercise.switchMap {
        liveData { emit(exerciseRepository.exercise(it.exercise)) }
    }
    
    val currentSets = trainingExercise.switchMap {
        trainingSetRepository.trainingSetsFlow(it.id).asLiveData()
    }
    
    private val previousExercise = trainingExercise.switchMap {
        liveData { emit(trainingExerciseRepository.previousTrainingExercise(it.trainingId, it.exercise)) }
    }
    
    val previousSets = previousExercise.switchMap {
        if (it == null) AbsentLiveData.create()
        else trainingSetRepository.trainingSetsFlow(it.trainingExerciseId).asLiveData()
    }
    
    val sets = MediatorLiveData<List<SetWithPreviousResults>>()
    
    
    val trainingExerciseFinishedEvent = MutableLiveData<Event<Unit>>()
    
    init {
        sets.addSource(currentSets) { sets.value = updateSets() }
        sets.addSource(previousSets) { sets.value = updateSets() }
    }
    
    private fun updateSets(): List<SetWithPreviousResults> {
        val currSets = currentSets.value
        val prevSets = previousSets.value
        val res = mutableListOf<SetWithPreviousResults>()
        for (i in 0 until max(currSets?.size ?: 0, prevSets?.size ?: 0)) {
            val curr = try { currSets?.get(i) } catch (e: Exception) { null }
            val prev = try { prevSets?.get(i) } catch (e: Exception) { null }
            val set = SetWithPreviousResults(
                    curr?.id ?: "", curr?.weight, curr?.reps, curr?.time, curr?.distance,
                    prev?.id ?: "", prev?.weight, prev?.reps, prev?.time, prev?.distance
            )
            res.add(set)
        }
        return res
    }

    fun deleteSet(id: String) = viewModelScope.launch {
        trainingSetRepository.delete(id)
    }
    
    fun finishExercise(trainingExerciseId: String)= viewModelScope.launch {
        trainingExerciseRepository.updateState(trainingExerciseId, TrainingExercise.FINISHED)
        trainingExercise.value?.let { exerciseRepository.increaseExecutionsCnt(it.exercise) }
        trainingExerciseFinishedEvent.value = Event(Unit)
    }
}