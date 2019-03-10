package com.dmitrysimakov.kilogram.util

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import android.view.ViewGroup

/**
 * A generic RecyclerView exercisePlannedListAdapter that uses Data Binding & DiffUtil.
 *
 * @param <T> Type of the items in the list
 * @param <V> The type of the ViewDataBinding
</V></T> */
abstract class DataBoundListAdapter<I : HasId, B : ViewDataBinding>(
        appExecutors: AppExecutors,
        var clickCallback: ((I) -> Unit)? = null
) : ListAdapter<I, DataBoundViewHolder<B>>(
        AsyncDifferConfig.Builder(IdDiffCallback<I>())
                .setBackgroundThreadExecutor(appExecutors.diskIO())
                .build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataBoundViewHolder(createBinding(parent))
    
    protected abstract fun createBinding(parent: ViewGroup): B
    
    override fun onBindViewHolder(holder: DataBoundViewHolder<B>, position: Int) {
        val item = getItem(position)
        with (holder.binding) {
            root.setOnClickListener { clickCallback?.invoke(item) }
            bind(this, item)
            executePendingBindings()
        }
    }
    
    protected abstract fun bind(binding: B, item: I)
    
    public override fun getItem(position: Int) : I = super.getItem(position)
}