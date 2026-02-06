package com.adrianhelo.contactsmanager.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Contact::class], version = 1)
abstract class ContactDB: RoomDatabase() {

    abstract val contactDao: ContactDao

    // Singleton Design Pattern
    companion object{
        @Volatile
        private var INSTANCE: ContactDB?=null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): ContactDB{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, ContactDB::class.java, "contacts_db").build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }

}