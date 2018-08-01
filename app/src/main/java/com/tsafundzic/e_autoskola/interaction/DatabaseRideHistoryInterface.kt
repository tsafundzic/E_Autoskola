package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.database.DatabaseReference

interface DatabaseRideHistoryInterface {

    fun getDatabaseRef(): DatabaseReference

    fun getCandidateData(userUid: String)

    fun getRideRoute(userUid: String, rideHourToShow: Int)

    fun getTimeRideStarted(userUid: String, rideHourToShow: Int)

    fun getLastRideHour(userUid: String)

    fun getTimeRideEnded(userUid: String, rideHourToShow: Int)

    fun getComment(userUid: String, rideHourToShow: Int)
}