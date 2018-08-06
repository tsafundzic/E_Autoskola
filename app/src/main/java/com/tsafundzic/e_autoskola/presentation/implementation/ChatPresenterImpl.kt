package com.tsafundzic.e_autoskola.presentation.implementation

import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.interaction.DatabaseChatInteractorImpl
import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.presentation.ChatInterface
import com.tsafundzic.e_autoskola.presentation.MainInterface

class ChatPresenterImpl(private val view: ChatInterface.View) : ChatInterface.Presenter, ChatInterface.onDatabaseListener, MainInterface.onLoginListener {

    private lateinit var currentInstructor: Instructor
    private lateinit var currentCandidate: Candidate

    private var databaseInteractor = DatabaseChatInteractorImpl(this)
    private var userInteractor: UserInteractorImpl = UserInteractorImpl(this)

    override fun startChat(instructor: Instructor) {
        view.startMessageActivity(instructor, currentCandidate.category, currentCandidate.name)
    }

    override fun startChat(candidate: Candidate) {
        view.startMessageActivity(candidate, currentInstructor.role, currentInstructor.name)
    }

    override fun returnCurrentInstructorData(instructor: Instructor) {
        currentInstructor = instructor
        databaseInteractor.getAssignedCandidates(instructor)
    }

    override fun returnCurrentCandidateData(candidate: Candidate) {
        currentCandidate = candidate
        databaseInteractor.getSelectedInstructor(candidate)
    }

    override fun returnInstructors(instructors: ArrayList<Instructor>) {
        view.showInstructorItems(instructors)
    }

    override fun getCandidatesList() {
        databaseInteractor.getCurrentInstructorData(userInteractor.getUserUid())
    }

    override fun returnAssignedCandidates(candidates: ArrayList<Candidate>) {
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