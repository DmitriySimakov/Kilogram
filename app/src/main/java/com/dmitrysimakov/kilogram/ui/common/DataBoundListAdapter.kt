package com.dmitrysimakov.kilogram.ui.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.util.AppExecutors

/**
 * A generic RecyclerView exercisePlannedListAdapter that uses Data Binding & DiffUtil.
 *
 * @param <T> Type of the items in the list
 * @param <B> The type of the ViewDataBinding
</B></T> */
abstract class DataBoundListAdapter<T, B : ViewDataBinding>(
        appExecutors: AppExecutors,
        var clickCallback: ((T) -> Unit)?,
        diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBoundListAdapter.DataBoundViewHolder<B>>(
        AsyncDifferConfig.Builder(diffCallback).setBackgroundThreadExecutor(appExecutors.diskIO()).build()
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
    
    protected abstract fun bind(binding: B, item: T)
    
    public override fun getItem(position: Int) : T = super.getItem(position)
    
    /**
     * A generic ViewHolder that works with a [ViewDataBinding].
     * @param <T> The type of the ViewDataBinding.
    </T> */
    class DataBoundViewHolder<out T : ViewDataBinding>(val binding: T): RecyclerView.ViewHolder(binding.root)
}