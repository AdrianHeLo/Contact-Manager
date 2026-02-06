package com.adrianhelo.contactsmanager.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrianhelo.contactsmanager.databinding.ContactCardViewBinding
import com.adrianhelo.contactsmanager.domain.Contact

class ContactsAdapter(private val itemList: List<Contact>, private val clickListener: (Contact)-> Unit): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>(){

    inner class ContactViewHolder(private val itemBinding: ContactCardViewBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(contact: Contact, clickListener: (Contact) -> Unit){
            itemBinding.nameFieldText.text = contact.name
            itemBinding.emailFieldText.text = contact.email
            itemBinding.listItemLayout.setOnClickListener{
               clickListener(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val bindingInflater: ContactCardViewBinding = DataBindingUtil.inflate(layoutInflater,com.adrianhelo.contactsmanager.R.layout.contact_card_view, parent, false)
        return ContactViewHolder(bindingInflater)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(itemList[position], clickListener)
    }
}