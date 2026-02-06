package com.adrianhelo.contactsmanager.data

import com.adrianhelo.contactsmanager.domain.Contact
import com.adrianhelo.contactsmanager.domain.ContactDao

class ContactRepository (private val contactDao: ContactDao){
    val contacts = contactDao.getContacts()

    suspend fun deleteContacts(){
        return contactDao.deleteAll()
    }

    suspend fun deleteContact(contact: Contact){
        return contactDao.deleteContact(contact)
    }

    suspend fun updateContact(contact: Contact){
        return contactDao.updateContact(contact)
    }

    suspend fun insertContact(contact: Contact): Long{
        return contactDao.insertContact(contact)
    }

}