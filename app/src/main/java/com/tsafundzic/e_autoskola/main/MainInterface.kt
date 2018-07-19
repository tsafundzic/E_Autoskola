package com.tsafundzic.e_autoskola.main

import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.BasePresenter

interface MainInterface {

    interface View {

        fun startCandidateMainActivity()

        fun startInstructorMainActivity()

        fun errorNotHaveAutorisation()

        fun errorWrongUserInformation()

    }

    interface Presenter : BasePresenter<View> {

        fun signIn(activity: MainActivity, email: String, password: String)

        fun checkForLoggedUser()

        fun onSuccessLogin(user: FirebaseUser)

    }

}