package com.rayadams.hiltexample.use_cases

import com.rayadams.hiltexample.repositories.ContactsRepository
import java.util.UUID
import javax.inject.Inject

class UpdateContactUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val dataValidUseCase: DataValidUseCase
) {

    operator fun invoke(id: UUID, firstName: String, lastName: String, phoneNumber: String) {
        if (dataValidUseCase(firstName, lastName, phoneNumber)) {
            contactsRepository.updateContact(id, firstName, lastName, phoneNumber)
        }
    }
}