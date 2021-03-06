package com.dmitrysimakov.kilogram.ui.common.create_post

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.model.Post
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.data_sources.FirebaseStorageSource
import com.dmitrysimakov.kilogram.data.remote.data_sources.PostSource
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.util.live_data.Event
import kotlinx.coroutines.launch

class CreatePostViewModel(
        private val postSrc: PostSource,
        private val programRepo: ProgramRepository,
        private val firebaseStorage: FirebaseStorageSource
) : ViewModel() {
    
    val user = MutableLiveData<User>()
    val title = MutableLiveData("")
    val content = MutableLiveData("")
    val imageUri = MutableLiveData<Uri?>()
    val program = MutableLiveData<Program>()
    
    val postPublishedEvent = MutableLiveData<Event<Unit>>()
    
    fun publishPost() { viewModelScope.launch {
        val author = user.value ?: return@launch
        
        val imageUri = imageUri.value?.let { firebaseStorage.uploadImage(it) }
        program.value?.let { programRepo.publishProgram(it) }
        
        val post = Post(author.id, author.name, author.photoUrl, title.value, content.value, imageUri, program.value)
        postSrc.publishPost(post)
        
        postPublishedEvent.value = Event(Unit)
    }}
}