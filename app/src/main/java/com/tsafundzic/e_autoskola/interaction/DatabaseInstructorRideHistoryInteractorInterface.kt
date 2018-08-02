package com.tsafundzic.e_autoskola.interaction

import android.content.Context
import com.google.firebase.database.DatabaseReference

interface DatabaseInstructorRideHistoryInteractorInterface {

    fun getCandidates(instructorUid: String, context: Context)

    fun getDatabaseRef(): DatabaseReference

    fun getLastRideHour(candidateId: String)

    fun getRideRoute(candidateId: String, rideHourToShow: Int)

    fun getTimeRideStarted(candidateId: String, rideHourToShow: Int)

    fun getTimeRideEnded(candidateId: String, rideHourToShow: Int)

    fun getComment(candidateId: String, rideHourToShow: Int)

}