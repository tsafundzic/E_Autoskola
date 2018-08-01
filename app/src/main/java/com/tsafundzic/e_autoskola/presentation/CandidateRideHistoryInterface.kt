package com.tsafundzic.e_autoskola.presentation

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

interface CandidateRideHistoryInterface {

    interface View {

        fun setCandidateName(name: String)

        fun setRideRoute(mLatlng: LatLng, bounds: LatLngBounds)

        fun setRideHour(rideHourToShow: Int)

        fun showErrorNoRide()

        fun setStartTime(startTime: String)

        fun setEndTime(endTime: String)

        fun setNoData()

        fun setComment(rideComment: String)

    }

    interface Presenter {

        fun getCandidateData()

        fun checkIfNextHourExist()

        fun getLastRideHour()

        fun checkIfPreviousExist()

    }

    interface onDatabaseListener {

        fun returnCandidateName(name: String)

        fun returnRideRouteLocations(mLatlng: LatLng)

        fun returnLastRideHour(lastRideHour: String?)

        fun returnStartedTime(startTime: String)

        fun returnEndedTime(endTime: String)

        fun returnNoData()

        fun returnComment(rideComment: String)

    }
}
