package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.presentation.MainInterface

class UserInteractorImpl(private var onLoginListener: MainInterface.onLoginListener) : UserInteractorInterface {

    var mAuth = FirebaseAuth.getInstance()

    fun performFirebaseLogin(email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        onLoginListener.onSuccess(user)
                    } else {
                        onLoginListener.onFailure()
                    }
                }
    }

    override fun checkLoggedUser(): FirebaseUser? {
        val user = mAuth.currentUser
        if (user != null) {
            onLoginListener.checkUserRole(user)
        } else {
            onLoginListener.turnOffProgressBar()
        }
        return user
    }

    override fun getUserUid(): String {
        return mAuth.uid.toString()
    }

    override fun signOutCurrentUser() {
        mAuth.signOut()
        onLoginListener.loggedOut()
    }

}