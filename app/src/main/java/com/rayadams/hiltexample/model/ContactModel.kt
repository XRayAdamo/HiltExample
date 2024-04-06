package com.rayadams.hiltexample.model

import java.util.UUID

data class ContactModel(
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)
