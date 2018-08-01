package com.tsafundzic.e_autoskola.interaction

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.firebase.database.*
import com.tsafundzic.e_autoskola.common.constants.*
import com.tsafundzic.e_autoskola.presentation.CandidateRideHistoryInterface
import java.util.HashMap
import com.google.firebase.database.DatabaseError


class DatabaseRideHistoryInteractorImpl(private var databaseListener: CandidateRideHistoryInterface.onDatabaseListener) : DatabaseRideHistoryInterface {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun getDatabaseRef(): DatabaseReference {
        return myRef
    }

    override fun getComment(userUid: String, rideHourToShow: Int) {
        val rideStarted = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists())
                    databaseListener.returnComment(dataSnapshot.children.first().value.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).child(userUid).child(RIDES).child(rideHourToShow.toString()).child(COMMENTS).addListenerForSingleValueEvent(rideStarted)
    }

    override fun getLastRideHour(userUid: String) {

        val lastRide = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                databaseListener.returnLastRideHour(dataSnapshot.children.first().key)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).child(userUid).child(RIDES).limitToLast(1).addListenerForSingleValueEvent(lastRide)
    }


    override fun getTimeRideEnded(userUid: String, rideHourToShow: Int) {
        val rideStarted = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists())
                    databaseListener.returnEndedTime(dataSnapshot.children.last().child(TIMESTAMP).value.toString())
                else
                    databaseListener.returnNoData()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).child(userUid).child(RIDES).child(rideHourToShow.toString()).child(rideHourToShow.toString()).orderByChild(TIMESTAMP).limitToLast(1).addListenerForSingleValueEvent(rideStarted)
    }

    override fun getTimeRideStarted(userUid: String, rideHourToShow: Int) {

        val rideStarted = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists())
                    databaseListener.returnStartedTime(dataSnapshot.children.first().child(TIMESTAMP).value.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).child(userUid).child(RIDES).child(rideHourToShow.toString()).child(rideHourToShow.toString()).orderByChild(TIMESTAMP).limitToFirst(1).addListenerForSingleValueEvent(rideStarted)
    }

    override fun getRideRoute(userUid: String, rideHourToShow: Int) {

        val queryRef = getDatabaseRef().child(USERS).child(userUid).child(RIDES).child(rideHourToShow.toString()).child(rideHourToShow.toString()).orderByChild(TIMESTAMP)

        queryRef.addChildEventListener(object : ChildEventListener {
            internal lateinit var bounds: LatLngBounds
            internal var builder = LatLngBounds.Builder()
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val data = dataSnapshot.value as Map<*, *>
                val timestamp = data[TIMESTAMP] as String


                val mCoordinate = data[LOCATION] as HashMap<*, *>
                val latitude = mCoordinate[LATIDUDE] as Double
                val longitude = mCoordinate[LONGITUDE] as Double

                val mLatlng = LatLng(latitude, longitude)

                databaseListener.returnRideRouteLocations(mLatlng)
                builder.include(mLatlng)
                bounds = builder.build()
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun getCandidateData(userUid: String) {
        val userName = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name = dataSnapshot.value.toString()

                databaseListener.returnCandidateName(name)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        myRef.child(USERS).child(userUid).child(NAME).addListenerForSingleValueEvent(userName)

    }
}