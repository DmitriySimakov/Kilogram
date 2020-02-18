package com.dmitrysimakov.kilogram.ui.home.photos.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.repository.PhotoRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoViewModel(private val repository: PhotoRepository) : ViewModel() {
    
    val photoUri = MutableLiveData<String>()
    
    val photo = photoUri.switchMap { liveData {
        emit(repository.photo(it))
    }}
    
    val photoDeletedEvent = MutableLiveData<Event<Unit>>()
    
    fun deletePhoto() {
        val uri = photoUri.value ?: return
        CoroutineScope(Dispatchers.IO).launch { repository.delete(uri) }
        photoDeletedEvent.value = Event(Unit)
    }
}