package com.example.aston_intensiv_3.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.aston_intensiv_3.model.Contact

object ContactDiffUtil : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}