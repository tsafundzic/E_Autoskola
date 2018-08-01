package com.tsafundzic.e_autoskola.presentation.implementation

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.firebase.auth.FirebaseUser
import com.tsafundzic.e_autoskola.interaction.DatabaseRideHistoryInteractorImpl
import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.presentation.CandidateRideHistoryInterface
import com.tsafundzic.e_autoskola.presentation.MainInterface

class CandidateRideHistoryPresenterImpl(private val view: CandidateRideHistoryInterface.View) : CandidateRideHistoryInterface.Presenter, CandidateRideHistoryInterface.onDatabaseListener, MainInterface.onLoginListener {


    private var databaseInteractor = DatabaseRideHistoryInteractorImpl(this)
    private var userInteractor: UserInteractorImpl = UserInteractorImpl(this)

    override fun returnComment(rideComment: String) {
        view.setComment(rideComment)
    }

    override fun returnNoData() {
        view.setNoData()
    }

    override fun returnEndedTime(endTime: String) {
        view.setEndTime(endTime)
    }

    override fun returnStartedTime(startTime: String) {
        view.setStartTime(startTime)
    }

    private var rideHourToShow = 1

    override fun checkIfPreviousExist() {
        rideHourToShow--
        if (rideHourToShow < 1) {
            view.showErrorNoRide()
            rideHourToShow = 1
        }
        getRideRoute()
        getTimeRideStarted()
        getTimeRideEnded()
        getComment()
        view.setRideHour(rideHourToShow)
    }

    override fun checkIfNextHourExist() {
        rideHourToShow++
        getRideRoute()
        getTimeRideStarted()
        getTimeRideEnded()
        getComment()
        view.setRideHour(rideHourToShow)
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

    override fun getLastRideHour() {
        databaseInteractor.getLastRideHour(userInteractor.getUserUid())
    }

    private fun getTimeRideStarted() {
        databaseInteractor.getTimeRideStarted(userInteractor.getUserUid(), rideHourToShow)
    }

    private fun getTimeRideEnded() {
        databaseInteractor.getTimeRideEnded(userInteractor.getUserUid(), rideHourToShow)
    }

    private fun getComment() {
        databaseInteractor.getComment(userInteractor.getUserUid(), rideHourToShow)
    }

    override fun returnRideRouteLocations(mLatlng: LatLng) {
        val bounds: LatLngBounds
        val builder = LatLngBounds.Builder()
        builder.include(mLatlng)
        bounds = builder.build()

        view.setRideRoute(mLatlng, bounds)
    }

    private fun getRideRoute() {
        databaseInteractor.getRideRoute(userInteractor.getUserUid(), rideHourToShow)
    }

    override fun returnCandidateName(name: String) {
        view.setCandidateName(name)
    }

    override fun getCandidateData() {
        databaseInteractor.getCandidateData(userInteractor.getUserUid())
    }

    override fun onSuccess(user: FirebaseUser?) {}

    override fun turnOffProgressBar() {}

    override fun onFailure() {}

    override fun checkUserRole(user: FirebaseUser) {}

    override fun loggedOut() {}
}