package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.database.*
import com.tsafundzic.e_autoskola.common.constants.CANDIDATE
import com.tsafundzic.e_autoskola.common.constants.NAME
import com.tsafundzic.e_autoskola.common.constants.ROLE
import com.tsafundzic.e_autoskola.common.constants.USERS
import com.tsafundzic.e_autoskola.presentation.QrCodeScannerInterface

class DatabaseQrScannerInteractorImpl(private var databaseListener: QrCodeScannerInterface.OnDatabaseListener) : DatabaseQrScannerInteractorInterface {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun getDatabaseRef(): DatabaseReference {
        return myRef
    }

    override fun getCandidateNAme(candidateId: String) {

        val candidateData = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val candidateName =
                        dataSnapshot.child(NAME).value.toString()
                val candidateRole = dataSnapshot.child(ROLE).value.toString()
                if (candidateName != "null" && candidateRole == CANDIDATE) {
                    databaseListener.returnCandidateIdAndName(candidateId, candidateName)
                } else {
                    databaseListener.returnDatabaseError()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        myRef.child(USERS).child(candidateId).addListenerForSingleValueEvent(candidateData)
    }


}