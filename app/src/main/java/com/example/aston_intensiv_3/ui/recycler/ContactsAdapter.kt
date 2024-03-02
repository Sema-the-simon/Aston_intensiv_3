package com.example.aston_intensiv_3.ui.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.aston_intensiv_3.R
import com.example.aston_intensiv_3.databinding.ContactItemBinding
import com.example.aston_intensiv_3.model.Contact
import com.example.aston_intensiv_3.ui.StateType

class ContactsAdapter(
    private val onClickAction: (Contact) -> Unit
) : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(
    ContactDiffUtil
) {

    private var state: StateType = StateType.IDLE

    fun setStateType(stateType: StateType) {
        state = stateType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater, parent, false)
        val holder = ContactViewHolder(binding)

        binding.root.setOnClickListener {
            val model = getItem(holder.adapterPosition)

            if (state == StateType.DELETE) {
                model.isSelected = !model.isSelected
                notifyItemChanged(holder.adapterPosition)
            } else {
                onClickAction(model)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model)
    }

    class ContactViewHolder(private val binding: ContactItemBinding) : ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            binding.contactItemId.text =
                binding.root.context.getString(R.string.contact_id, contact.id.toString())
            binding.tvContactItemName.text = contact.name
            binding.tvContactItemSurname.text = contact.surname
            binding.tvContactItemPhoneNumber.text = contact.phoneNumber
            if (contact.isSelected) {
                binding.cardContainer.apply {
                    strokeColor = Color.RED
                    strokeWidth = 8
                }
            } else {
                binding.cardContainer.apply {
                    strokeColor = Color.BLACK
                    strokeWidth = 2
                }
            }
        }
    }
}