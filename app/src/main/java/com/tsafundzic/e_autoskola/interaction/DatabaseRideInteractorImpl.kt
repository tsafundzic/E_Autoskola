package com.tsafundzic.e_autoskola.interaction

import android.location.Location
import com.google.firebase.database.*
import com.tsafundzic.e_autoskola.common.constants.*
import com.tsafundzic.e_autoskola.presentation.RideInterface
import kotlinx.android.synthetic.main.activity_ride.*
import java.text.SimpleDateFormat
import java.util.*

class DatabaseRideInteractorImpl(private var databaseListener: RideInterface.OnDatabaseListener) : DatabaseRideInteractorInterface {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun getDatabaseRef(): DatabaseReference {
        return myRef
    }

    override fun saveComment(comment: String, candidateId: String, currentRideHour: Int) {


        getDatabaseRef().child(USERS).child(candidateId).child(RIDES).child(currentRideHour.toString()).child(COMMENTS).push().setValue(comment)
    }

    override fun getCurrentRideHour(candidateId: String) {


        val rideHour = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    val lastRideHour: String = dataSnapshot.children.last().key.toString()

                    databaseListener.returnLastRideHour(lastRideHour)
                } else {
                    databaseListener.returnLastRideHour("0")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).child(candidateId).child(RIDES).addListenerForSingleValueEvent(rideHour)

    }

    override fun saveToDatabase(candidateId: String, mLastLocation: Location, currentRideHour: Int) {
        getDatabaseRef().keepSynced(true)

        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = Date()
        val mLastUpdateTime = dateFormat.format(date).toString()
        val mLocations = HashMap<String, Any>()
        mLocations[TIMESTAMP] = mLastUpdateTime
        val mCoordinate = HashMap<String, Any>()
        mCoordinate[LATIDUDE] = mLastLocation.latitude
        mCoordinate[LONGITUDE] = mLastLocation.longitude
        mLocations[LOCATION] = mCoordinate

        getDatabaseRef().child(USERS).child(candidateId).child(RIDES).child(currentRideHour.toString()).child(currentRideHour.toString()).push().setValue(mLocations)

    }

    override fun getCandidateNAme(candidateId: String) {

        val candidateData = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val candidateName = dataSnapshot.child(NAME).value.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        myRef.child(USERS).child(candidateId).addListenerForSingleValueEvent(candidateData)
    }

}