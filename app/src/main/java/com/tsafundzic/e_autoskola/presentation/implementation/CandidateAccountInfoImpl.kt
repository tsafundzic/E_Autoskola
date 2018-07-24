package com.tsafundzic.e_autoskola.presentation.implementation

import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.interaction.DatabaseInteractorImpl
import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.models.School
import com.tsafundzic.e_autoskola.presentation.CandidateAccountInfoInterface
import com.tsafundzic.e_autoskola.presentation.MainInterface

class CandidateAccountInfoImpl(private var view: CandidateAccountInfoInterface.View) : CandidateAccountInfoInterface.Presenter, MainInterface.onLoginListener, MainInterface.onDatabaseListener {

    private var userInteractor: UserInteractorImpl = UserInteractorImpl(this)
    private var databaseInteractor: DatabaseInteractorImpl = DatabaseInteractorImpl(this)

    override fun getCandidateData() {
        databaseInteractor.getCandidateData(userInteractor.getUserUid())
        databaseInteractor.getCandidateImage(userInteractor.getUserUid())
    }

    override fun returnCandidate(candidate: Candidate) {
        view.setUserData(candidate)
    }

    override fun setUserImage(imageUrl: String) {
        view.setImage(imageUrl)
    }

    override fun performSignOut() {
        userInteractor.signOutCurrentUser()
    }

    override fun loggedOut() {
        view.finishActivity()
    }
    override fun returnSchool(school: School) {
        view.setSchoolData(school)
    }

    override fun getSchoolInfo(schoolId: String) {
        databaseInteractor.getSchoolInfoData(schoolId)
    }

    override fun getInstructorInfo(instructorUid: String) {
        databaseInteractor.getInstructorData(instructorUid)
    }

    override fun returnInstructor(instructor: Instructor) {
        view.setInstructorData(instructor)
    }

    override fun onSuccess(user: FirebaseUser?) {}

    override fun turnOffProgressBar() {}

    override fun onFailure() {}

    override fun checkUserRole(user: FirebaseUser) {}

    override fun getUserRole(user: FirebaseUser?) {}

    override fun setViewToInstructorMainActivity() {}

    override fun setViewToCandidateMainActivity() {}

    override fun setAuthorisationError() {}

}