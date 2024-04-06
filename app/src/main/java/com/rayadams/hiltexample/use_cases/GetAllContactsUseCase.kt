package com.rayadams.hiltexample.use_cases

import com.rayadams.hiltexample.model.ContactModel
import com.rayadams.hiltexample.repositories.ContactsRepository
import javax.inject.Inject

class GetAllContactsUseCase @Inject constructor(private val contactsRepository: ContactsRepository) {
    operator fun invoke(): List<ContactModel> =
        contactsRepository.contacts.sortedWith(compareBy(
            { it.firstName }, { it.lastName }
        ))
}