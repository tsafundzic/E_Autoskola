package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.auth.FirebaseUser

interface UserInteractorInterface {

    fun signOutCurrentUser()

    fun checkLoggedUser(): FirebaseUser?

    fun getUserUid(): String

}