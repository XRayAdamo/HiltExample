package com.rayadams.hiltexample.repositories

import com.rayadams.hiltexample.model.ContactModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.util.UUID
import javax.inject.Singleton

@Singleton
class ContactsRepository {

    private val _contacts = mutableListOf<ContactModel>()
    val contacts: List<ContactModel> = _contacts

    /**
     * This flow event keeps subscribers informed whenever the data changes,
     * regardless of its source (database or memory).
     * It's a powerful approach for applications that require immediate updates for all interested parties.
     */
    private val _onDataChanged =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val onDataChanged: SharedFlow<Unit> = _onDataChanged

    fun addContact(contactModel: ContactModel) {
        _contacts.add(contactModel)
        notifySubscribers()
    }

    fun getContactByID(id: UUID): ContactModel? = _contacts.firstOrNull { it.id == id }

    fun deleteContact(contactModel: ContactModel) {
        _contacts.remove(contactModel)
        notifySubscribers()
    }

    fun updateContact(id: UUID, firstName: String, lastName: String, phoneNumber: String) {
        val index = _contacts.indexOfFirst { it.id == id }
        if (index != -1) {
            _contacts[index] = _contacts[index].copy(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
            notifySubscribers()
        }
    }

    /**
     * Alert subscribers to data updates and clear the notification replay cache to ensure new subscribers only receive current notifications.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun notifySubscribers(){
        _onDataChanged.tryEmit(Unit)
        _onDataChanged.resetReplayCache()
    }

}
