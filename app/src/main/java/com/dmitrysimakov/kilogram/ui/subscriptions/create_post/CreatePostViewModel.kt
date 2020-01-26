package com.dmitrysimakov.kilogram.ui.subscriptions.create_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.remote.models.Post
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.live_data.Event
import com.dmitrysimakov.kilogram.util.postsCollection
import com.dmitrysimakov.kilogram.util.setNewValue
import java.util.*

class CreatePostViewModel : ViewModel() {
    
    val title = MutableLiveData("")
    val content = MutableLiveData("")
    val imageUrl = MutableLiveData<String>(null)
    
    private val _user = MutableLiveData<User>()
    
    private val _postPublishedEvent = MutableLiveData<Event<Unit>>()
    val postPublishedEvent: LiveData<Event<Unit>> = _postPublishedEvent
    
    fun setUser(user: User?) { _user.setNewValue(user) }
    
    fun publishPost() {
        val postDoc = postsCollection.document()
        val author = _user.value ?: return
        val post = Post(postDoc.id, author.id, author.name, author.photoUrl, title.value, content.value, imageUrl.value, Date())
        
        postDoc.set(post).addOnSuccessListener {
            _postPublishedEvent.value = Event(Unit)
        }
    }
}