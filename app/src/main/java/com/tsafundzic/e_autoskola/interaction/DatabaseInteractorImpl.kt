package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.tsafundzic.e_autoskola.common.constants.*
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.presentation.MainInterface


class DatabaseInteractorImpl(private var databaseListener: MainInterface.onDatabaseListener) : DatabaseInteractorInterface {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun getDatabaseRef(): DatabaseReference {
        return myRef
    }

    override fun getUserRole(user: FirebaseUser?) {

        val userRoleListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userRole = dataSnapshot.value.toString()
                when (userRole) {
                    CANDIDATE -> databaseListener.setViewToCandidateMainActivity()
                    INSTRUCTOR -> databaseListener.setViewToInstructorMainActivity()
                    else -> databaseListener.setAuthorisationError()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        if (user != null) {
            myRef.child(USERS).child(user.uid).child(ROLE).addListenerForSingleValueEvent(userRoleListener)
        }

    }

    override fun getInstructorData(uid: String) {

        val instructorData = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val instructor = Instructor(dataSnapshot.child(ADDRESS).value.toString(),
                        dataSnapshot.child(BIRTHDATE).value.toString(),
                        dataSnapshot.child(ROLE).value.toString(),
                        dataSnapshot.child(PHONE).value.toString(),
                        dataSnapshot.child(NAME).value.toString(),
                        dataSnapshot.child(OIB).value.toString(),
                        dataSnapshot.child(SELECTEDSCHOOL).value.toString(),
                        dataSnapshot.child(EMAIL).value.toString())
                databaseListener.returnInstructor(instructor)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        myRef.child(USERS).child(uid).addListenerForSingleValueEvent(instructorData)
    }

    override fun getInstructorImage(uid: String) {

        FirebaseStorage.getInstance().reference.child("$INSTRUCTORS/$uid").downloadUrl
                .addOnSuccessListener({ uri ->
                    val imageURL: String = uri.toString()
                    databaseListener.setUserImage(imageURL)

                }).addOnFailureListener({})
    }

    override fun getCandidateData(uid: String) {

        val candidateData = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val candidate = Candidate(dataSnapshot.child(ADDRESS).value.toString(),
                        dataSnapshot.child(BIRTHDATE).value.toString(),
                        dataSnapshot.child(CATEGORY).value.toString(),
                        dataSnapshot.child(EMAIL).value.toString(),
                        dataSnapshot.child(NAME).value.toString(),
                        dataSnapshot.child(OIB).value.toString(),
                        dataSnapshot.child(PAYMENT).value.toString(),
                        dataSnapshot.child(PHONE).value.toString(),
                        dataSnapshot.child(ROLE).value.toString(),
                        dataSnapshot.child(SELECTEDSCHOOL).value.toString(),
                        dataSnapshot.child(SELECTEDINSTRUCTOR).value.toString(),
                        dataSnapshot.child(PASSEDDRIVETEST).value.toString(),
                        dataSnapshot.child(PASSEDFIRSTAID).value.toString(),
                        dataSnapshot.child(PASSEDTRAFFICREGULATIONS).value.toString()
                )

                databaseListener.returnCandidate(candidate)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        myRef.child(USERS).child(uid).addListenerForSingleValueEvent(candidateData)
    }

    override fun getCandidateImage(uid: String) {
        FirebaseStorage.getInstance().reference.child("$CANDIDATES/$uid").downloadUrl
                .addOnSuccessListener({ uri ->
                    val imageURL: String = uri.toString()
                    databaseListener.setUserImage(imageURL)

                }).addOnFailureListener({})
    }

}