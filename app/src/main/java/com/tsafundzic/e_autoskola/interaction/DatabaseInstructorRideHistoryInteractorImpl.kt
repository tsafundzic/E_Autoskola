package com.tsafundzic.e_autoskola.interaction

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.firebase.database.*
import com.tsafundzic.e_autoskola.common.constants.*
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.presentation.InstructorRideHistoryInterface
import java.util.HashMap

class DatabaseInstructorRideHistoryInteractorImpl(private var databaseListener: InstructorRideHistoryInterface.onDatabaseListener) : DatabaseInstructorRideHistoryInteractorInterface {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun getDatabaseRef(): DatabaseReference {
        return myRef
    }

    override fun getCandidates(instructorUid: String, context: Context) {

        val rideHour = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val candidates = ArrayList<Candidate>()

                for (userSnapshot in dataSnapshot.children) {
                    if (userSnapshot.child(SELECTEDINSTRUCTOR).value == instructorUid) {
                        val candidate = Candidate(userSnapshot.child(ADDRESS).value.toString(),
                                userSnapshot.child(BIRTHDATE).value.toString(),
                                //candidate ID saved to candidate CATEGORY
                                userSnapshot.key.toString(),
                                userSnapshot.child(EMAIL).value.toString(),
                                userSnapshot.child(NAME).value.toString(),
                                userSnapshot.child(OIB).value.toString(),
                                userSnapshot.child(PAYMENT).value.toString(),
                                userSnapshot.child(PHONE).value.toString(),
                                userSnapshot.child(ROLE).value.toString(),
                                userSnapshot.child(SELECTEDSCHOOL).value.toString(),
                                userSnapshot.child(SELECTEDINSTRUCTOR).value.toString(),
                                userSnapshot.child(PASSEDDRIVETEST).value.toString(),
                                userSnapshot.child(PASSEDFIRSTAID).value.toString(),
                                userSnapshot.child(PASSEDTRAFFICREGULATIONS).value.toString()
                        )
                        candidates.add(candidate)
                    }
                }
                candidates.sortBy { it.name }
                databaseListener.returnCandidates(candidates, context)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).addListenerForSingleValueEvent(rideHour)
    }

    override fun getComment(candidateId: String, rideHourToShow: Int) {
        val rideStarted = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists())
                    databaseListener.returnComment(dataSnapshot.children.first().value.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).child(candidateId).child(RIDES).child(rideHourToShow.toString()).child(COMMENTS).addListenerForSingleValueEvent(rideStarted)
    }

    override fun getTimeRideEnded(candidateId: String, rideHourToShow: Int) {
        val rideStarted = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists())
                    databaseListener.returnEndedTime(dataSnapshot.children.last().child(TIMESTAMP).value.toString())
                else
                    databaseListener.returnNoData()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).child(candidateId).child(RIDES).child(rideHourToShow.toString()).child(rideHourToShow.toString()).orderByChild(TIMESTAMP).limitToLast(1).addListenerForSingleValueEvent(rideStarted)
    }

    override fun getTimeRideStarted(candidateId: String, rideHourToShow: Int) {
        val rideStarted = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists())
                    databaseListener.returnStartedTime(dataSnapshot.children.first().child(TIMESTAMP).value.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).child(candidateId).child(RIDES).child(rideHourToShow.toString()).child(rideHourToShow.toString()).orderByChild(TIMESTAMP).limitToFirst(1).addListenerForSingleValueEvent(rideStarted)
    }

    override fun getRideRoute(candidateId: String, rideHourToShow: Int) {
        val queryRef = getDatabaseRef().child(USERS).child(candidateId).child(RIDES).child(rideHourToShow.toString()).child(rideHourToShow.toString()).orderByChild(TIMESTAMP)

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

    override fun getLastRideHour(candidateId: String) {
        val lastRide = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists())
                    databaseListener.returnLastRideHour(dataSnapshot.children.first().key)
                else
                    databaseListener.returnCandidatHasNoRideRecord()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).child(candidateId).child(RIDES).limitToLast(1).addListenerForSingleValueEvent(lastRide)
    }
}