package com.rayadams.hiltexample.use_cases

import com.rayadams.hiltexample.model.ContactModel
import com.rayadams.hiltexample.repositories.ContactsRepository
import javax.inject.Inject

class AddContactUseCase @Inject constructor(private val contactsRepository: ContactsRepository,
    private val dataValidUseCase: DataValidUseCase) {
    operator fun invoke (firstName:String, lastName:String, phoneNumber:String) {
        if (dataValidUseCase(firstName, lastName, phoneNumber)) {
            contactsRepository.addContact(
                ContactModel(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber
                )
            )
        }
    }
}
