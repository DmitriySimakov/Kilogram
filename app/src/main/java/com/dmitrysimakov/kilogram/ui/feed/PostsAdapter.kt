package com.dmitrysimakov.kilogram.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.Post
import com.dmitrysimakov.kilogram.data.model.PostDiffCallback
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.databinding.ItemPostBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class PostsAdapter(
        private val sharedVM: SharedViewModel,
        private val programClickCallback: ((Program) -> Unit),
        private val likeClickCallback: ((Post) -> Unit)
) : DataBoundListAdapter<Post, ItemPostBinding>(null, PostDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemPostBinding = ItemPostBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemPostBinding, item: Post) {
        binding.sharedVM = sharedVM
        binding.post = item
        
        item.program?.let { program ->
            binding.program.setOnClickListener { programClickCallback(program) }
        }
        binding.likeBtn.setOnClickListener { likeClickCallback(item) }
    }
}