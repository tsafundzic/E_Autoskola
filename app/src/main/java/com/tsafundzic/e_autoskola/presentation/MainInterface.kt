package com.tsafundzic.e_autoskola.presentation

import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.ui.main.MainActivity

interface MainInterface {

    interface View {

        fun startCandidateMainActivity()

        fun turnOffProgress()

        fun startInstructorMainActivity()

        fun errorNotHaveAutorisation()

        fun errorWrongUserInformation()

        fun setEmailError()
        fun setPasswordError()

    }

    interface Presenter : BasePresenter<View> {

        fun signIn(activity: MainActivity, email: String, password: String)

        fun checkForLoggedUser()

        fun onSuccessLogin(user: FirebaseUser)

    }

}