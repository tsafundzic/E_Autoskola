package com.tsafundzic.e_autoskola.presentation

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.tsafundzic.e_autoskola.ui.instructorMain.RideActivity

interface RideInterface {

    interface View {

        fun showAlertLocationPermissionNeeded()

        fun startBuildingGoogleApiClient()

        fun startCheckingLocationPermission()

        fun requestLocationUpdate()

        fun setStartedTime(startedTime: String)

        fun setRideHour(lastRideHour: Int?)

        fun rideFinishedSuccessful()

        fun setErrorNoComments()

    }

    interface Presenter {

        fun getCandidateName(candidateId: String)

        fun checkLocationPermission(context: Context, activity: RideActivity)

        fun fineLocationPermission(applicationContext: Context, googleMap: GoogleMap?)

        fun checkPermission(applicationContext: Context)

        fun getCurrentTime()

        fun saveToDatabase(candidateId: String, mLastLocation: Location)

        fun getRideNumber(candidateId: String)

        fun checkComments(comment: String, candidateId: String)

    }

    interface OnDatabaseListener {

        fun returnLastRideHour(lastRideHour: String?)

    }
}