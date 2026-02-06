package com.adrianhelo.contactsmanager.ui.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianhelo.contactsmanager.data.ContactRepository
import com.adrianhelo.contactsmanager.domain.Contact
import kotlinx.coroutines.launch

class ContactVM (private val contactRepository: ContactRepository): ViewModel(), Observable{
    val contacts = contactRepository.contacts
    private var isUpdateOrDelete = false
    private lateinit var contactToUpdateOrDelete: Contact

    // MutableLiveData
    @Bindable
    val inputName = MutableLiveData<String?>()
    @Bindable
    val inputEmail = MutableLiveData<String?>()
    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearOrDeleteAllButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteAllButtonText.value = "Clear All"
    }

    // 2. Implementar Repositorio
    private fun insertContact(contact: Contact) = viewModelScope.launch {
        contactRepository.insertContact(contact)
    }

    private fun updateContact(contact: Contact) = viewModelScope.launch {
        contactRepository.updateContact(contact)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteAllButtonText.value = "Clear All"
    }

    private fun deleteContact(contact: Contact) = viewModelScope.launch {
        contactRepository.deleteContact(contact)
        // Resetting the buttons and fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteAllButtonText.value = "Clear All"
    }

    private fun deleteContacts() = viewModelScope.launch {
        contactRepository.deleteContacts()
    }

    fun saveOrUpdate(){
        if (isUpdateOrDelete){
            contactToUpdateOrDelete.name = inputName.value!!
            contactToUpdateOrDelete.email = inputEmail.value!!
            updateContact(contactToUpdateOrDelete)
        }else{
            val name = inputName.value!!
            val email = inputEmail.value!!
            insertContact(Contact(0, name, email))
            // Reset the name and email
            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearAllOrDelete(){
        if (isUpdateOrDelete){
            deleteContact(contactToUpdateOrDelete)
        }else{
            deleteContacts()
        }
    }

    fun initUpdateAndDelete(contact: Contact){
        inputName.value = contact.name
        inputEmail.value = contact.email
        isUpdateOrDelete = true
        contactToUpdateOrDelete = contact
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteAllButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}