package com.rayadams.hiltexample.navigation

object NavigationParams {
    const val CONTACT_ID = "contactId"
}
object NavigationPath {
    const val CONTACTS_VIEW = "contactView"
    const val ADD_CONTACTS_VIEW = "addContactView"
    const val EDIT_CONTACT_VIEW = "editContactView"
    const val EDIT_CONTACT_VIEW_ID = "editContactView/{${NavigationParams.CONTACT_ID}}"
}