package com.tsafundzic.e_autoskola.presentation

import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor

interface MainInterface {

    interface View {

        fun startCandidateMainActivity()

        fun turnOffProgress()

        fun startInstructorMainActivity()

        fun errorNotHaveAuthorisation()

        fun errorWrongUserInformation()

        fun setEmailError()

        fun setPasswordError()

    }

    interface Presenter {

        fun signIn(email: String, password: String)

        fun checkForLoggedUser()

    }

    interface onLoginListener {

        fun onSuccess(user: FirebaseUser?)

        fun turnOffProgressBar()

        fun onFailure()

        fun checkUserRole(user: FirebaseUser)

        fun loggedOut()
    }

    interface onDatabaseListener {

        fun getUserRole(user: FirebaseUser?)

        fun setViewToInstructorMainActivity()

        fun setViewToCandidateMainActivity()

        fun setAuthorisationError()

        fun returnInstructor(instructor: Instructor)

        fun setUserImage(imageUrl: String)

        fun returnCandidate(candidate: Candidate)
    }

}