package com.tsafundzic.e_autoskola.database

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.tsafundzic.common.constants.ROLE
import com.tsafundzic.common.constants.USERS
import com.tsafundzic.e_autoskola.main.MainInterface

class DatabaseInteractorImpl : DatabaseInteractorInterface {

    private lateinit var mainInterface: MainInterface.Presenter

    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference()

    override fun getDatabaseRef(): DatabaseReference {
        return myRef
    }


    //private  var userRole: String = ""

    override fun getUserRole(user: FirebaseUser) {

        val userRoleListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

               var userRole = dataSnapshot.value.toString()
                //mainInterface.onSuccessLogin(userRole)

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }

        myRef.child(USERS).child(user.uid).child(ROLE).addListenerForSingleValueEvent(userRoleListener)

       // return userRole

    }


}