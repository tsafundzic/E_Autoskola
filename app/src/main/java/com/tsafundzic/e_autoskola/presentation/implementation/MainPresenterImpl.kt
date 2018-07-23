package com.tsafundzic.e_autoskola.presentation.implementation

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tsafundzic.e_autoskola.common.constants.CANDIDATE
import com.tsafundzic.e_autoskola.common.constants.INSTRUCTOR
import com.tsafundzic.e_autoskola.common.constants.ROLE
import com.tsafundzic.e_autoskola.common.constants.USERS
import com.tsafundzic.e_autoskola.common.helpers.isValidEmail
import com.tsafundzic.e_autoskola.interaction.DatabaseInteractorInterface
import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.presentation.MainInterface
import com.tsafundzic.e_autoskola.ui.main.MainActivity


class MainPresenterImpl(private var databaseInteractor: DatabaseInteractorInterface, private var userInteractor: UserInteractorImpl) : MainInterface.Presenter {

    private lateinit var view: MainInterface.View

    override fun setView(view: MainInterface.View) {
        this.view = view
    }

    override fun signIn(activity: MainActivity, email: String, password: String) {
        if (email.isValidEmail()) {
            if (password.isNotEmpty()) {
                userInteractor.mAuth
                        .signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = userInteractor.mAuth.currentUser
                                if (user != null) {
                                    checkUserRole(user)
                                }
                            } else {
                                view.errorWrongUserInformation()
                            }

                        }
                userInteractor.performFirebaseLogin(activity, email, password)
            } else {
                view.setPasswordError()
            }
        } else {
            view.setEmailError()
        }
    }

    override fun onSuccessLogin(user: FirebaseUser) {
        view.startCandidateMainActivity()
        databaseInteractor.getUserRole(user)
    }

    override fun checkForLoggedUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        ifUserIsLogged(currentUser)
    }

    private fun ifUserIsLogged(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            checkUserRole(currentUser)
        } else {
            view.turnOffProgress()
        }
    }

    private fun checkUserRole(user: FirebaseUser) {
        val userRoleListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userRole = dataSnapshot.value.toString()
                if (userRole == CANDIDATE) {
                    view.startCandidateMainActivity()
                } else if (userRole == INSTRUCTOR) {
                    view.startInstructorMainActivity()
                } else {
                    view.errorNotHaveAutorisation()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        databaseInteractor.getDatabaseRef().child(USERS).child(user.uid).child(ROLE).addListenerForSingleValueEvent(userRoleListener)

    }


}