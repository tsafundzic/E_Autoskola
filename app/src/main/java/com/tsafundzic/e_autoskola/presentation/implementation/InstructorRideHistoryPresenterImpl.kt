package com.tsafundzic.e_autoskola.presentation.implementation

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.interaction.DatabaseInstructorRideHistoryInteractorImpl
import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.presentation.InstructorRideHistoryInterface
import com.tsafundzic.e_autoskola.presentation.MainInterface

class InstructorRideHistoryPresenterImpl(private var view: InstructorRideHistoryInterface.View) : InstructorRideHistoryInterface.Presenter, MainInterface.onLoginListener, InstructorRideHistoryInterface.onDatabaseListener {

    private var userInteractor: UserInteractorImpl = UserInteractorImpl(this)
    private var databaseInteractor: DatabaseInstructorRideHistoryInteractorImpl = DatabaseInstructorRideHistoryInteractorImpl(this)

    override fun returnCandidates(candidates: ArrayList<String>, context: Context) {
        val candidateAdapter = ArrayAdapter<String>(context, R.layout.simple_spinner_item, candidates)
        candidateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.setSpinner(candidates, candidateAdapter)
    }

    override fun getCandidates(context: Context) {
        databaseInteractor.getCandidates(userInteractor.getUserUid(), context)
    }


    override fun onSuccess(user: FirebaseUser?) {}

    override fun turnOffProgressBar() {}

    override fun onFailure() {}

    override fun checkUserRole(user: FirebaseUser) {}

    override fun loggedOut() {}

}