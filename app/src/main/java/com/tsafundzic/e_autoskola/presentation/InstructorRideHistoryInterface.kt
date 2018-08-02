package com.tsafundzic.e_autoskola.presentation

import android.content.Context
import android.widget.ArrayAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.tsafundzic.e_autoskola.models.Candidate

interface InstructorRideHistoryInterface {

    interface View {

        fun setSpinner(candidates: ArrayList<Candidate>, candidateAdapter: ArrayAdapter<String>)

        fun setRideHour(rideHourToShow: Int)

        fun setRideRoute(mLatlng: LatLng, bounds: LatLngBounds)

        fun setStartTime(startTime: String)

        fun setEndTime(endTime: String)

        fun setComment(rideComment: String)

        fun setNoData()

    }

    interface Presenter {

        fun getCandidates(context: Context)

        fun candidateReselected(position: Int)

        fun getLastRideHour()

        fun checkIfPreviousExist()

        fun checkIfNextHourExist()

    }

    interface onDatabaseListener {

        fun returnCandidates(candidates: ArrayList<Candidate>, context: Context)

        fun returnLastRideHour(lastRideHour: String?)

        fun returnRideRouteLocations(mLatlng: LatLng)

        fun returnStartedTime(startTime: String)

        fun returnEndedTime(endTime: String)

        fun returnComment(comment: String)

        fun returnNoData()

        fun returnCandidatHasNoRideRecord()

    }
}