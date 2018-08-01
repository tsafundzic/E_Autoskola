package com.tsafundzic.e_autoskola.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.constants.CANDIDATEID
import com.tsafundzic.e_autoskola.common.constants.CANDIDATENAME
import com.tsafundzic.e_autoskola.common.constants.PERMISSIONSREQUESTLOCATION
import com.tsafundzic.e_autoskola.common.extensions.getIntent
import com.tsafundzic.e_autoskola.common.helpers.toast
import com.tsafundzic.e_autoskola.presentation.RideInterface
import com.tsafundzic.e_autoskola.presentation.implementation.RidePresenterImpl
import kotlinx.android.synthetic.main.activity_ride.*

class RideActivity : AppCompatActivity(), RideInterface.View, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    companion object {
        fun getLaunchIntent(from: Context, candidateId: String, candidateName: String) = from.getIntent<RideActivity>().apply {
            putExtra(CANDIDATEID, candidateId)
            putExtra(CANDIDATENAME, candidateName)
        }
    }

    lateinit var presenter: RideInterface.Presenter
    private lateinit var mGoogleMap: GoogleMap

    private lateinit var mGoogleApiClient: GoogleApiClient

    lateinit var mLocationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        injectDependencies()
        timeCounter.start()

        endRide.setOnClickListener { finishRide() }
    }

    override fun onBackPressed() {}
    override fun setErrorNoComments() {
        comments.error = getString(R.string.emptyCommentError)
    }

    override fun rideFinishedSuccessful() {
        toast(getString(R.string.rideFinishedSuccessful))
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)

        finish()
    }

    override fun setRideHour(lastRideHour: Int?) {

        rideHourNumber.text = lastRideHour.toString()

    }

    override fun setStartedTime(startedTime: String) {
        rideStartedTime.text = startedTime
    }

    @SuppressLint("MissingPermission")
    override fun requestLocationUpdate() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
    }

    override fun startCheckingLocationPermission() {
        checkLocationPermission()
    }

    override fun startBuildingGoogleApiClient() {
        buildGoogleApiClient()
    }

    override fun showAlertLocationPermissionNeeded() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.LocationPermissionNeeded))
                .setMessage(getString(R.string.LocationPermissionNeededExplanation))
                .setPositiveButton(getString(R.string.ok)) { dialogInterface, i ->
                    //Prompt the user once explanation has been shown
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            PERMISSIONSREQUESTLOCATION)
                }
                .create()
                .show()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {}

    @SuppressLint("RestrictedApi")
    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 30000
        mLocationRequest.fastestInterval = 30000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        presenter.checkPermission(applicationContext)

    }

    override fun onConnectionSuspended(p0: Int) {}

    private fun finishRide() {
        presenter.checkComments(comments.text.toString(), intent.getStringExtra(CANDIDATEID))

    }

    private fun injectDependencies() {
        presenter = RidePresenterImpl(this)

        presenter.getCurrentTime()
        candidateNameShow.text = intent.getStringExtra(CANDIDATENAME)

        presenter.getRideNumber(intent.getStringExtra(CANDIDATEID))
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        mGoogleMap = googleMap!!
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        presenter.fineLocationPermission(applicationContext, googleMap)
    }

    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient.connect()

    }


    override fun onLocationChanged(location: Location) {

        presenter.saveToDatabase(intent.getStringExtra(CANDIDATEID), location)

        val latLng = LatLng(location.latitude, location.longitude)

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

    }


    private fun checkLocationPermission() {

        presenter.checkLocationPermission(applicationContext, this)


    }

}
