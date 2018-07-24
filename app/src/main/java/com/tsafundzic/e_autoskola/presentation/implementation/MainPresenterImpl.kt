package com.tsafundzic.e_autoskola.presentation.implementation


import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.common.helpers.isValidEmail
import com.tsafundzic.e_autoskola.interaction.DatabaseInteractorImpl
import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.models.School
import com.tsafundzic.e_autoskola.presentation.MainInterface


class MainPresenterImpl(private var view: MainInterface.View) : MainInterface.Presenter, MainInterface.onLoginListener, MainInterface.onDatabaseListener {

    private var userInteractor: UserInteractorImpl = UserInteractorImpl(this)
    private var databaseInteractor: DatabaseInteractorImpl = DatabaseInteractorImpl(this)

    override fun signIn(email: String, password: String) {
        if (email.isValidEmail()) {
            if (password.isNotEmpty()) {
                userInteractor.performFirebaseLogin(email, password)
            } else {
                view.setPasswordError()
            }
        } else {
            view.setEmailError()
        }
    }

    override fun checkUserRole(user: FirebaseUser) {
        databaseInteractor.getUserRole(user)
    }

    override fun checkForLoggedUser() {
        val user = userInteractor.checkLoggedUser()
    }

    override fun onFailure() {
        view.errorWrongUserInformation()
    }

    override fun turnOffProgressBar() {
        view.turnOffProgress()
    }

    override fun setViewToCandidateMainActivity() {
        view.startCandidateMainActivity()
    }

    override fun setAuthorisationError() {
        view.errorNotHaveAuthorisation()
    }

    override fun setViewToInstructorMainActivity() {
        view.startInstructorMainActivity()
    }

    override fun getUserRole(user: FirebaseUser?) {
        databaseInteractor.getUserRole(user)
    }

    override fun onSuccess(user: FirebaseUser?) {
        if (user != null) {
            databaseInteractor.getUserRole(user)
        }
    }

    override fun loggedOut() { }

    override fun returnSchool(school: School) {}

    override fun returnCandidate(candidate: Candidate) {}

    override fun setUserImage(uid: String) {}

    override fun returnInstructor(instructor: Instructor) {}
}