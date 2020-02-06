package com.dmitrysimakov.kilogram.ui.home.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.model.Photo
import com.dmitrysimakov.kilogram.databinding.ItemPhotoBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class PhotosAdapter(clickCallback: ((Photo) -> Unit)? = null)
    : DataBoundListAdapter<Photo, ItemPhotoBinding>(clickCallback, PhotoDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemPhotoBinding = ItemPhotoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemPhotoBinding, item: Photo) {
        binding.photo = item
    }
}

private class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
            oldItem.uri == newItem.uri
    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
            oldItem == newItem
}