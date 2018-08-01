package com.tsafundzic.e_autoskola.interaction

import android.content.Context
import com.google.firebase.database.*
import com.tsafundzic.e_autoskola.common.constants.*
import com.tsafundzic.e_autoskola.presentation.InstructorRideHistoryInterface

class DatabaseInstructorRideHistoryInteractorImpl(private var databaseListener: InstructorRideHistoryInterface.onDatabaseListener) : DatabaseInstructorRideHistoryInteractorInterface {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun getDatabaseRef(): DatabaseReference {
        return myRef
    }

    override fun getCandidates(instructorUid: String, context: Context) {

        val rideHour = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val candidates = ArrayList<String>()

                for (userSnapshot in dataSnapshot.children) {
                    if ( userSnapshot.child(SELECTEDINSTRUCTOR).value == instructorUid ) {
                        candidates.add(userSnapshot.key.toString())
                    }
                }
                databaseListener.returnCandidates(candidates, context)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).addListenerForSingleValueEvent(rideHour)
    }
}