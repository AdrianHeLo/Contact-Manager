package com.adrianhelo.contactsmanager.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianhelo.contactsmanager.R
import com.adrianhelo.contactsmanager.data.ContactRepository
import com.adrianhelo.contactsmanager.databinding.ActivityMainBinding
import com.adrianhelo.contactsmanager.domain.Contact
import com.adrianhelo.contactsmanager.domain.ContactDB
import com.adrianhelo.contactsmanager.ui.viewmodel.ContactVM
import com.adrianhelo.contactsmanager.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var contactViewModel: ContactVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ROOM INSTANCE
        val dao = ContactDB.getInstance(applicationContext).contactDao
        val repository = ContactRepository(dao)
        val factory = ViewModelFactory(repository)

        // View Model
        contactViewModel = ViewModelProvider(this, factory).get(ContactVM::class.java)
        activityMainBinding.contactVM = contactViewModel
        activityMainBinding.lifecycleOwner = this

        //Methods
        initRecyclerView()
    }

    private fun initRecyclerView() {
        activityMainBinding.containerRecyclerView.layoutManager = LinearLayoutManager(this)
        displayUsersList()
    }

    private fun displayUsersList() {
        contactViewModel.contacts.observe(this) {
            activityMainBinding.containerRecyclerView.adapter =
                ContactsAdapter(it, { selectItem: Contact -> listItemClicked(selectItem) }
                )
        }
    }

    private fun listItemClicked(selectItem: Contact) {
        Toast.makeText(this, "Selected name is ${selectItem.name}", Toast.LENGTH_LONG).show()
        contactViewModel.initUpdateAndDelete(selectItem)
    }
}