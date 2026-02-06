package com.adrianhelo.contactsmanager.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val contactId: Int,

    @ColumnInfo(name = "contact_name")
    var name: String,

    @ColumnInfo(name = "contact_email")
    var email: String
)
