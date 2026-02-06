package com.adrianhelo.contactsmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adrianhelo.contactsmanager.data.ContactRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val contactRepository: ContactRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactVM::class.java)){
            return ContactVM(contactRepository) as T
        }
        throw IllegalArgumentException("Unknow View Model Class")
    }

}