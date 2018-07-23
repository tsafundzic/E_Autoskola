package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.tsafundzic.e_autoskola.common.constants.ROLE
import com.tsafundzic.e_autoskola.common.constants.USERS
import com.tsafundzic.e_autoskola.presentation.MainInterface

class DatabaseInteractorImpl : DatabaseInteractorInterface {

    private lateinit var mainInterface: MainInterface.Presenter

    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference()

    override fun getDatabaseRef(): DatabaseReference {
        return myRef
    }

    override fun getUserRole(user: FirebaseUser) {

        val userRoleListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

               var userRole = dataSnapshot.value.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }

        myRef.child(USERS).child(user.uid).child(ROLE).addListenerForSingleValueEvent(userRoleListener)

    }


}