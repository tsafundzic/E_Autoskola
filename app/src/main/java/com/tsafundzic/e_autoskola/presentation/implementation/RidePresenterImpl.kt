package com.tsafundzic.e_autoskola.presentation.implementation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.tsafundzic.e_autoskola.common.constants.PERMISSIONSREQUESTLOCATION
import com.tsafundzic.e_autoskola.interaction.DatabaseRideInteractorImpl
import com.tsafundzic.e_autoskola.presentation.RideInterface
import com.tsafundzic.e_autoskola.ui.RideActivity
import java.text.SimpleDateFormat
import java.util.*

class RidePresenterImpl(private var view: RideInterface.View) : RideInterface.Presenter, RideInterface.OnDatabaseListener {
    override fun checkComments(comment: String, candidateId: String, currentRideHour: Int) {
        if (comment.isNotEmpty()) {
            databaseInteractor.saveComment(comment, candidateId, currentRideHour)
            view.rideFinishedSuccessful()
        } else {
            view.setErrorNoComments()
        }
    }

    override fun returnLastRideHour(lastRideHour: String?) {
        val number = lastRideHour?.toIntOrNull()
        view.setRideHour(number)
    }

    override fun getRideNumber(candidateId: String) {
        databaseInteractor.getCurrentRideHour(candidateId)
    }

    override fun saveToDatabase(candidateId: String, mLastLocation: Location, currentRideHour: Int) {
        databaseInteractor.saveToDatabase(candidateId, mLastLocation, currentRideHour)
    }

    override fun getCurrentTime() {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = Date()
        view.setStartedTime(dateFormat.format(date).toString())
    }

    private var databaseInteractor: DatabaseRideInteractorImpl = DatabaseRideInteractorImpl(this)

    override fun checkPermission(applicationContext: Context) {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            view.requestLocationUpdate()
        }

    }

    override fun fineLocationPermission(applicationContext: Context, googleMap: GoogleMap?) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                view.startBuildingGoogleApiClient()
                googleMap?.isMyLocationEnabled = true
            } else {

                view.startCheckingLocationPermission()
            }
        } else {
            view.startBuildingGoogleApiClient()
            googleMap?.isMyLocationEnabled = true
        }
    }


    override fun checkLocationPermission(context: Context, activity: RideActivity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                view.showAlertLocationPermissionNeeded()


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSIONSREQUESTLOCATION)
            }
        }
    }

    override fun getCandidateName(candidateId: String) {
        databaseInteractor.getCandidateNAme(candidateId)
    }

}