package com.dmitrysimakov.kilogram.util

import androidx.databinding.ViewDataBinding

/**
 * A generic ViewHolder that works with a [ViewDataBinding].
 * @param <T> The type of the ViewDataBinding.
</T> */
class DataBoundViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)