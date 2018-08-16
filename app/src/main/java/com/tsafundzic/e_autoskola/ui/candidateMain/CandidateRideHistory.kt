package com.tsafundzic.e_autoskola.ui.candidateMain


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.presentation.CandidateRideHistoryInterface
import com.tsafundzic.e_autoskola.presentation.implementation.CandidateRideHistoryPresenterImpl
import kotlinx.android.synthetic.main.fragment_candidate_ride_history.*


class CandidateRideHistory : Fragment(), CandidateRideHistoryInterface.View, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private lateinit var presenter: CandidateRideHistoryInterface.Presenter

    private lateinit var mGoogleMap: GoogleMap

    private lateinit var mGoogleApiClient: GoogleApiClient

    lateinit var mLocationRequest: LocationRequest


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_candidate_ride_history, container, false)

        val nextRideHour: ImageButton = view.findViewById(R.id.nextHour)
        val previousRideHour: ImageButton = view.findViewById(R.id.previousHour)
        injectDependencies()

        setRideData()
        nextRideHour.setOnClickListener { nextHourChecked() }
        previousRideHour.setOnClickListener { previousHourChecked() }

        return view
    }

    override fun setComment(rideComment: String) {
        if (isAdded)
            comment.text = rideComment
    }

    override fun setNoData() {
        if (isAdded) {
            startTimeDate.text = ""
            endTimeDate.text = ""
            comment.text = ""
        }
    }

    override fun setEndTime(endTime: String) {
        if (isAdded)
            endTimeDate.text = endTime
    }

    override fun setStartTime(startTime: String) {
        if (isAdded)
            startTimeDate.text = startTime
    }

    override fun showErrorNoRide() {

    }

    override fun setRideHour(rideHourToShow: Int) {
        if (isAdded)
            rideHourNumber.text = rideHourToShow.toString()
    }

    override fun setRideRoute(mLatlng: LatLng, bounds: LatLngBounds) {
        val mMarkerOption = MarkerOptions()
                .position(mLatlng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_location))
        val mMarker = mGoogleMap.addMarker(mMarkerOption)

       mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatlng, 11.0f))
    }

    override fun setCandidateName(name: String) {
        if (isAdded)
            candidateName.text = name
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        buildGoogleApiClient()
    }

    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(context!!)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient.connect()
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        }
    }

    override fun onConnectionSuspended(p0: Int) {}

    override fun onConnectionFailed(p0: ConnectionResult) {}

    override fun onLocationChanged(p0: Location?) {}


    private fun setRideData() {
        presenter.getCandidateData()
    }

    private fun nextHourChecked() {
        presenter.checkIfNextHourExist()
        mGoogleMap.clear()
    }

    private fun previousHourChecked() {
        presenter.checkIfPreviousExist()
        mGoogleMap.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = this.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun injectDependencies() {
        presenter = CandidateRideHistoryPresenterImpl(this)
        presenter.getLastRideHour()
    }


}
