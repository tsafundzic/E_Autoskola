package com.tsafundzic.e_autoskola.presentation.implementation

import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.interaction.DatabaseInteractorImpl
import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.models.School
import com.tsafundzic.e_autoskola.presentation.InstructorAccountInfoInterface
import com.tsafundzic.e_autoskola.presentation.MainInterface

class InstructorAccountInfoImpl(private var view: InstructorAccountInfoInterface.View) : InstructorAccountInfoInterface.Presenter, MainInterface.onLoginListener, MainInterface.onDatabaseListener {

    private var userInteractor: UserInteractorImpl = UserInteractorImpl(this)
    private var databaseInteractor: DatabaseInteractorImpl = DatabaseInteractorImpl(this)

    override fun setUserImage(imageUrl: String) {
        view.setImage(imageUrl)
    }

    override fun returnInstructor(instructor: Instructor) {
        view.setUserData(instructor)
    }

    override fun getInstructorData() {
        databaseInteractor.getInstructorData(userInteractor.getUserUid())
        databaseInteractor.getInstructorImage(userInteractor.getUserUid())
    }

    override fun loggedOut() {
        view.finishActivity()
    }

    override fun performSignOut() {
        userInteractor.signOutCurrentUser()
    }

    override fun getSchoolInfo(schoolId: String) {
        databaseInteractor.getSchoolInfoData(schoolId)
    }

    override fun returnSchool(school: School) {
        view.setSchoolData(school)
    }

    override fun onSuccess(user: FirebaseUser?) {}

    override fun turnOffProgressBar() {}

    override fun onFailure() {}

    override fun checkUserRole(user: FirebaseUser) {}

    override fun getUserRole(user: FirebaseUser?) {}

    override fun setViewToInstructorMainActivity() {}

    override fun setViewToCandidateMainActivity() {}

    override fun setAuthorisationError() {}

    override fun returnCandidate(candidate: Candidate) {}


}