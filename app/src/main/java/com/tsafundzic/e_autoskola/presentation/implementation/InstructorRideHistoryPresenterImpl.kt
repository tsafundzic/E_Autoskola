package com.tsafundzic.e_autoskola.presentation.implementation

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.interaction.DatabaseInstructorRideHistoryInteractorImpl
import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.presentation.InstructorRideHistoryInterface
import com.tsafundzic.e_autoskola.presentation.MainInterface

class InstructorRideHistoryPresenterImpl(private var view: InstructorRideHistoryInterface.View) : InstructorRideHistoryInterface.Presenter, MainInterface.onLoginListener, InstructorRideHistoryInterface.onDatabaseListener {

    private var userInteractor: UserInteractorImpl = UserInteractorImpl(this)
    private var databaseInteractor: DatabaseInstructorRideHistoryInteractorImpl = DatabaseInstructorRideHistoryInteractorImpl(this)

    private var candidatesList = ArrayList<Candidate>()

    private  var candidateId: String = ""

    private var rideHourToShow = 1

    override fun returnCandidatHasNoRideRecord() {
        rideHourToShow=1
        view.setRideHour(rideHourToShow)
        view.setNoData()
    }

    override fun returnNoData() {
        view.setNoData()
    }

    override fun checkIfNextHourExist() {
        rideHourToShow++
        getRideRoute()
        getTimeRideStarted()
        getTimeRideEnded()
        getComment()
        view.setRideHour(rideHourToShow)
    }

    override fun checkIfPreviousExist() {
        rideHourToShow--
        if (rideHourToShow < 1) {
            //view.showErrorNoRide()
            rideHourToShow = 1
        }
        getRideRoute()
        getTimeRideStarted()
        getTimeRideEnded()
        getComment()
        view.setRideHour(rideHourToShow)
    }

    override fun returnComment(comment: String) {
        view.setComment(comment)
    }

    override fun returnEndedTime(endTime: String) {
        view.setEndTime(endTime)
    }

    override fun returnStartedTime(startTime: String) {
        view.setStartTime(startTime)
    }

    override fun returnRideRouteLocations(mLatlng: LatLng) {
        val bounds: LatLngBounds
        val builder = LatLngBounds.Builder()
        builder.include(mLatlng)
        bounds = builder.build()

        view.setRideRoute(mLatlng, bounds)
    }

    override fun returnLastRideHour(lastRideHour: String?) {
        if (lastRideHour != null) {
            rideHourToShow = lastRideHour.toInt()
            view.setRideHour(rideHourToShow)

            getRideRoute()
            getTimeRideStarted()
            getTimeRideEnded()
            getComment()
        }
    }

    private fun getComment() {
        databaseInteractor.getComment(candidateId, rideHourToShow)
    }

    private fun getTimeRideEnded() {
        databaseInteractor.getTimeRideEnded(candidateId, rideHourToShow)    }

    private fun getRideRoute() {
        databaseInteractor.getRideRoute(candidateId, rideHourToShow)
    }

    override fun getLastRideHour() {
        databaseInteractor.getLastRideHour(candidateId)
    }


    override fun candidateReselected(position: Int) {
        candidateId = candidatesList[position].category
        getLastRideHour()
    }

    private fun getTimeRideStarted() {
        databaseInteractor.getTimeRideStarted(candidateId, rideHourToShow)
    }


    override fun returnCandidates(candidates: ArrayList<Candidate>, context: Context) {
        val candidatesName = ArrayList<String>()
        for (candidate in candidates) {
            candidatesName.add(candidate.name)
        }
        val candidateAdapter = ArrayAdapter<String>(context, R.layout.simple_spinner_item, candidatesName)
        candidateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        candidatesList = candidates
        candidateId = candidatesList.first().category

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