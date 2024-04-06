package com.rayadams.hiltexample.use_cases

import com.rayadams.hiltexample.model.ContactModel
import com.rayadams.hiltexample.repositories.ContactsRepository
import javax.inject.Inject

class DeleteContactUseCase @Inject constructor(private val contactsRepository: ContactsRepository) {
    operator fun invoke(contact: ContactModel) {
        contactsRepository.deleteContact(contact)
    }
}