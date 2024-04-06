package com.rayadams.hiltexample.views.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.rayadams.hiltexample.model.ContactModel
import com.rayadams.hiltexample.navigation.CustomNavigator
import com.rayadams.hiltexample.navigation.NavigationParams
import com.rayadams.hiltexample.use_cases.DataValidUseCase
import com.rayadams.hiltexample.use_cases.GetContactByIdUseCase
import com.rayadams.hiltexample.use_cases.UpdateContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditContactViewModel @Inject constructor(
    state: SavedStateHandle,
    getContactByIdUseCase: GetContactByIdUseCase,
    private val dataValidUseCase: DataValidUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val customNavigator: CustomNavigator
) : ViewModel() {
    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var phoneNumber by mutableStateOf("")
        private set

    var canBeSaved by mutableStateOf(false)
        private set

    private var contactToEdit: ContactModel? = null

    init {
        // Usually parameters and data from repository
        // must be validated here, but we will assume the good path

        val contactId = state[NavigationParams.CONTACT_ID] ?: ""

        contactToEdit = getContactByIdUseCase(UUID.fromString(contactId))

        firstName = contactToEdit!!.firstName
        lastName = contactToEdit!!.lastName
        phoneNumber = contactToEdit!!.phoneNumber
    }

    private fun updateCanBeSaved() {
        canBeSaved = dataValidUseCase(firstName, lastName, phoneNumber)
    }

    fun updateFirstName(value: String) {
        firstName = value

        updateCanBeSaved()
    }

    fun updateLastName(value: String) {
        lastName = value

        updateCanBeSaved()
    }

    fun updatePhoneNumber(value: String) {
        phoneNumber = value

        updateCanBeSaved()
    }

    fun save() {
        if (!canBeSaved) {
            return
        }

        updateContactUseCase(contactToEdit!!.id, firstName, lastName, phoneNumber)

        goBack()
    }

    fun goBack() {
        customNavigator.goBack()
    }
}