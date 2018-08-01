package com.tsafundzic.e_autoskola.interaction

import android.location.Location
import com.google.firebase.database.DatabaseReference

interface DatabaseRideInteractorInterface {

    fun getDatabaseRef(): DatabaseReference

    fun getCandidateNAme(candidateId: String)

    fun saveToDatabase(candidateId: String, mLastLocation: Location, currentRideHour: Int)

    fun getCurrentRideHour(candidateId: String)

    fun saveComment(comment: String, candidateId: String, currentRideHour: Int)
}