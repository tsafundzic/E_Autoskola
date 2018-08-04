package com.tsafundzic.e_autoskola.presentation.implementation

import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.interaction.DatabaseChatInteractorImpl
import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.presentation.ChatInterface
import com.tsafundzic.e_autoskola.presentation.MainInterface

class ChatPresenterImpl(private val view: ChatInterface.View) : ChatInterface.Presenter, ChatInterface.onDatabaseListener, MainInterface.onLoginListener {
    override fun startChat(instructor: Instructor) {
        view.startMessageActivity(instructor)
    }

    override fun startChat(candidate: Candidate) {
        view.startMessageActivity(candidate)
    }

    private var candidatesList = ArrayList<Candidate>()
    private var instructorsList = ArrayList<Instructor>()

    private var databaseInteractor = DatabaseChatInteractorImpl(this)
    private var userInteractor: UserInteractorImpl = UserInteractorImpl(this)

    override fun returnCurrentInstructorData(instructor: Instructor) {
        databaseInteractor.getAssignedCandidates(instructor)
    }

    override fun returnCurrentCandidateData(candidate: Candidate) {
        databaseInteractor.getSelectedInstructor(candidate)
    }

    override fun returnInstructors(instructors: ArrayList<Instructor>) {
        instructorsList = instructors
        view.showInstructorItems(instructors)
    }

    override fun getCandidatesList() {
        databaseInteractor.getCurrentInstructorData(userInteractor.getUserUid())
    }

    override fun returnAssignedCandidates(candidates: ArrayList<Candidate>) {
        candidatesList = candidates
        view.showCandidatesItems(candidates)
    }

    override fun getInstructorList() {
        databaseInteractor.getCurrentCandidateData(userInteractor.getUserUid())
    }

    override fun onSuccess(user: FirebaseUser?) {}

    override fun turnOffProgressBar() {}

    override fun onFailure() {}

    override fun checkUserRole(user: FirebaseUser) {}

    override fun loggedOut() {}
}