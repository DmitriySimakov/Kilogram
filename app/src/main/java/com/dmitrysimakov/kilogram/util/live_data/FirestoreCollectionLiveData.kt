package com.dmitrysimakov.kilogram.util.live_data

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import timber.log.Timber

fun <T> CollectionReference.liveData(clazz: Class<T>): LiveData<List<T>> {
    return CollectionLiveDataNative(this, clazz)
}

fun <T> CollectionReference.liveData(parser: (documentSnapshot: DocumentSnapshot) -> T): LiveData<List<T>> {
    return CollectionLiveDataCustom(this, parser)
}

fun CollectionReference.liveData(): LiveData<QuerySnapshot> {
    return CollectionLiveDataRaw(this)
}

private class CollectionLiveDataNative<T>(private val collectionReference: CollectionReference,
                                          private val clazz: Class<T>) : LiveData<List<T>>() {
    
    private var listener: ListenerRegistration? = null
    
    override fun onActive() {
        super.onActive()
        
        listener = collectionReference.addSnapshotListener { querySnapshot, exception ->
            if (exception == null) {
                value = querySnapshot?.documents?.map { it.toObject(clazz)!! }
            } else {
                Timber.e(exception)
            }
        }
    }
    
    override fun onInactive() {
        super.onInactive()
        
        listener?.remove()
        listener = null
    }
}

private class CollectionLiveDataCustom<T>(private val collectionReference: CollectionReference,
                                          private val parser: (documentSnapshot: DocumentSnapshot) -> T) : LiveData<List<T>>() {
    
    private var listener: ListenerRegistration? = null
    
    override fun onActive() {
        super.onActive()
        
        listener = collectionReference.addSnapshotListener { querySnapshot, exception ->
            if (exception == null) {
                value = querySnapshot?.documents?.map { parser.invoke(it) }
            } else {
                Timber.e(exception)
            }
        }
    }
    
    override fun onInactive() {
        super.onInactive()
        
        listener?.remove()
        listener = null
    }
}

private class CollectionLiveDataRaw(private val collectionReference: CollectionReference) : LiveData<QuerySnapshot>() {
    
    private var listener: ListenerRegistration? = null
    
    override fun onActive() {
        super.onActive()
        
        listener = collectionReference.addSnapshotListener { querySnapshot, exception ->
            if (exception == null) {
                value = querySnapshot
            } else {
                Timber.e(exception)
            }
        }
    }
    
    override fun onInactive() {
        super.onInactive()
        
        listener?.remove()
        listener = null
    }
}