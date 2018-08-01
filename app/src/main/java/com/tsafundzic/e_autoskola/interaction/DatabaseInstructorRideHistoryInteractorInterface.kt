package com.tsafundzic.e_autoskola.interaction

import android.content.Context
import com.google.firebase.database.DatabaseReference

interface DatabaseInstructorRideHistoryInteractorInterface {

    fun getCandidates(instructorUid: String, context: Context)

    fun getDatabaseRef(): DatabaseReference
}