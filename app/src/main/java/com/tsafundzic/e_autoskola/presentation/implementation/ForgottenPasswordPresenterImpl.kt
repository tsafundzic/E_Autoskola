package com.tsafundzic.e_autoskola.presentation.implementation

import com.tsafundzic.e_autoskola.common.helpers.isValidEmail
import com.tsafundzic.e_autoskola.presentation.ForgottenPasswordInterface
import com.google.firebase.auth.FirebaseAuth


class ForgottenPasswordPresenterImpl(private var view: ForgottenPasswordInterface.View) : ForgottenPasswordInterface.Presenter {
    override fun checkEmail(email: String) {
        if (email.isValidEmail()) {

            val auth = FirebaseAuth.getInstance()

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            view.onSuccess()
                        } else {
                            view.onFailure()
                        }
                    }

        } else {
            view.showEmailError()
        }
    }
}