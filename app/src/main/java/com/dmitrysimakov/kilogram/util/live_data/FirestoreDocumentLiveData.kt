package com.dmitrysimakov.kilogram.util.live_data

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import timber.log.Timber

fun <T> DocumentReference.liveData(clazz: Class<T>): LiveData<T> {
    return DocumentLiveDataNative(this, clazz)
}

fun <T> DocumentReference.liveData(parser: (documentSnapshot: DocumentSnapshot) -> T): LiveData<T> {
    return DocumentLiveDataCustom(this, parser)
}

fun DocumentReference.liveData(): LiveData<DocumentSnapshot> {
    return DocumentLiveDataRaw(this)
}

private class DocumentLiveDataNative<T>(private val documentReference: DocumentReference,
                                        private val clazz: Class<T>) : LiveData<T>() {
    
    private var listener: ListenerRegistration? = null
    
    override fun onActive() {
        super.onActive()
        
        listener = documentReference.addSnapshotListener { documentSnapshot, exception ->
            if (exception == null) {
                documentSnapshot?.let {
                    value = it.toObject(clazz)
                }
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

class DocumentLiveDataCustom<T>(private val documentReference: DocumentReference,
                                private val parser: (documentSnapshot: DocumentSnapshot) -> T) : LiveData<T>() {
    
    private var listener: ListenerRegistration? = null
    
    override fun onActive() {
        super.onActive()
        
        listener = documentReference.addSnapshotListener { documentSnapshot, exception ->
            if (exception == null) {
                documentSnapshot?.let {
                    value = parser.invoke(it)
                }
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

class DocumentLiveDataRaw(private val documentReference: DocumentReference) : LiveData<DocumentSnapshot>() {
    
    private var listener: ListenerRegistration? = null
    
    override fun onActive() {
        super.onActive()
        
        listener = documentReference.addSnapshotListener { documentSnapshot, exception ->
            if (exception == null) {
                value = documentSnapshot
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