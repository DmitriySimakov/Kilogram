package com.dmitrysimakov.kilogram.ui.community.people

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.remote.Person
import com.dmitrysimakov.kilogram.databinding.ItemPersonBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class PeopleListAdapter(
        private val sendMessageClickCallback: ((Person) -> Unit),
        clickCallback: ((Person) -> Unit)? = null
) : DataBoundListAdapter<Person, ItemPersonBinding>(
        clickCallback,
        PersonDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemPersonBinding = ItemPersonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemPersonBinding, item: Person) {
        binding.person = item
        binding.sendMessageBtn.setOnClickListener { sendMessageClickCallback.invoke(item) }
    }
}

private class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Person, newItem: Person) =
            oldItem == newItem
}