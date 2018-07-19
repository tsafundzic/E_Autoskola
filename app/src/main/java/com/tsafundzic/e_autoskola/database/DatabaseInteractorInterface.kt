package com.tsafundzic.e_autoskola.database

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

interface DatabaseInteractorInterface {

    fun getDatabaseRef(): DatabaseReference

    fun getUserRole(user: FirebaseUser)

}