package com.dmitrysimakov.kilogram.util

import androidx.recyclerview.widget.DiffUtil

class IdDiffCallback<T : HasId> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem._id == newItem._id

    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}