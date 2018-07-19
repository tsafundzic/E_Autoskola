package com.tsafundzic.e_autoskola.database

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.tsafundzic.e_autoskola.main.MainActivity

class UserInteractorImpl : UserInteractorInterface{

    var mAuth = FirebaseAuth.getInstance()

    //private lateinit var main : MainInterface.Presenter
    override fun performFirebaseLogin(activity: MainActivity, email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val user = mAuth.currentUser

                        if (user != null) {
                           // main.onSuccessLogin(user)
                        }


                        //view.getLoggedUser(user)
                        //updateUI(user)

                        //Toast.makeText(this, R.string.successfulSignIn, Toast.LENGTH_SHORT).show()

                    } else {

                        // If sign in fails, display a message to the user.
                        Log.w("signInWithEmail:failure", task.exception)
                        //Toast.makeText(this, R.string.signInError, Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }

                }
    }

}