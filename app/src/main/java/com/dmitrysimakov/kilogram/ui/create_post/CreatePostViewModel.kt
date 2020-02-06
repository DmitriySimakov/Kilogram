package com.dmitrysimakov.kilogram.ui.create_post

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.model.Post
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.util.imagesRef
import com.dmitrysimakov.kilogram.util.live_data.Event
import com.dmitrysimakov.kilogram.util.postsCollection
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import com.dmitrysimakov.kilogram.data.model.Program as LocalProgram

class CreatePostViewModel : ViewModel() {
    
    val user = MutableLiveData<User>()
    val title = MutableLiveData("")
    val content = MutableLiveData("")
    val imageUri = MutableLiveData<Uri?>()
    val program = MutableLiveData<LocalProgram>()
    
    val postPublishedEvent = MutableLiveData<Event<Unit>>()
    
    fun publishPost() { viewModelScope.launch {
        val author = user.value ?: return@launch
    
        val imageUrl = imageUri.value?.lastPathSegment
        imageUrl?.let { imagesRef.child(it).putFile(imageUri.value!!) }
        
        val postDoc = postsCollection.document()
        val post = Post(postDoc.id, author.id, author.name, author.photoUrl, title.value, content.value, imageUrl, null, Date())
        
        postDoc.set(post).await()
        postPublishedEvent.value = Event(Unit)
    }}
}