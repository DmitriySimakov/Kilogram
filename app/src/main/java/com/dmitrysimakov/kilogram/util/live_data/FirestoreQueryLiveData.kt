package com.dmitrysimakov.kilogram.util.live_data

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import timber.log.Timber

fun <T> Query.liveData(clazz: Class<T>): LiveData<List<T>> {
    return QueryLiveDataNative(this, clazz)
}

fun <T> Query.liveData(parser: (documentSnapshot: DocumentSnapshot) -> T): LiveData<List<T>> {
    return QueryLiveDataCustom(this, parser)
}

fun Query.liveData(): LiveData<QuerySnapshot> {
    return QueryLiveDataRaw(this)
}

private class QueryLiveDataNative<T>(
        private val query: Query,
        private val clazz: Class<T>
) : LiveData<List<T>>() {
    
    private var listener: ListenerRegistration? = null
    
    override fun onActive() {
        super.onActive()
        
        listener = query.addSnapshotListener { querySnapshot, exception ->
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

private class QueryLiveDataCustom<T>(
        private val query: Query,
        private val parser: (documentSnapshot: DocumentSnapshot) -> T
) : LiveData<List<T>>() {
    
    private var listener: ListenerRegistration? = null
    
    override fun onActive() {
        super.onActive()
        
        listener = query.addSnapshotListener { querySnapshot, exception ->
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

private class QueryLiveDataRaw(private val query: Query) : LiveData<QuerySnapshot>() {
    
    private var listener: ListenerRegistration? = null
    
    override fun onActive() {
        super.onActive()
        
        listener = query.addSnapshotListener { querySnapshot, exception ->
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