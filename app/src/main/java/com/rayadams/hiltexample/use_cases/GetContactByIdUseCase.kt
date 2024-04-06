package com.rayadams.hiltexample.use_cases

import com.rayadams.hiltexample.model.ContactModel
import com.rayadams.hiltexample.repositories.ContactsRepository
import java.util.UUID
import javax.inject.Inject

class GetContactByIdUseCase @Inject constructor(private val contactsRepository: ContactsRepository) {
    operator fun invoke(id: UUID): ContactModel? = contactsRepository.getContactByID(id)

}