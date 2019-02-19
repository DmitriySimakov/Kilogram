package com.dmitrysimakov.kilogram.util

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup

/**
 * A generic RecyclerView adapter that uses Data Binding & DiffUtil.
 *
 * @param <T> Type of the items in the list
 * @param <V> The type of the ViewDataBinding
</V></T> */
abstract class DataBoundListAdapter<T : HasId, V : ViewDataBinding>(
        appExecutors: AppExecutors,
        protected var clickCallback: ((T) -> Unit)?
) : ListAdapter<T, DataBoundViewHolder<V>>(
        AsyncDifferConfig.Builder(IdDiffCallback<T>())
                .setBackgroundThreadExecutor(appExecutors.diskIO())
                .build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataBoundViewHolder(createBinding(parent))

    fun get(position: Int) : T = getItem(position)
    
    fun setClickListener(callback : ((T) -> Unit)) {
        clickCallback = callback
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: V, item: T)
}