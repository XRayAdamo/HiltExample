package com.rayadams.hiltexample.views.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadams.hiltexample.model.ContactModel
import com.rayadams.hiltexample.navigation.CustomNavigator
import com.rayadams.hiltexample.navigation.NavigationPath
import com.rayadams.hiltexample.repositories.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val customNavigator: CustomNavigator,
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    val data = mutableStateListOf<ContactModel>()

    init {
        viewModelScope.launch {
            contactsRepository.onDataChanged.collect {
                loadData()
            }
        }

        loadData()
    }

    private fun loadData() {
        data.clear()
        data.addAll(contactsRepository.contacts)
    }

    fun editContact(contact: ContactModel) {
        customNavigator.navigate(NavigationPath.EDIT_CONTACT_VIEW + "/${contact.id}")
    }

    fun goToAddNewContact() {
        customNavigator.navigate(NavigationPath.ADD_CONTACTS_VIEW)
    }

    fun deleteContact(contact: ContactModel) {

    }

}