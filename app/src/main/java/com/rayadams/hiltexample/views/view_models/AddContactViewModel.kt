package com.rayadams.hiltexample.views.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rayadams.hiltexample.navigation.CustomNavigator
import com.rayadams.hiltexample.use_cases.AddContactUseCase
import com.rayadams.hiltexample.use_cases.DataValidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val dataValidUseCase: DataValidUseCase,
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

        addContactUseCase(firstName, lastName, phoneNumber)

        goBack()
    }

    fun goBack() {
        customNavigator.goBack()
    }
}