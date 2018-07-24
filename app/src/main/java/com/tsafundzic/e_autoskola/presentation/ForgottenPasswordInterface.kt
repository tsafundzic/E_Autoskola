package com.tsafundzic.e_autoskola.presentation

interface ForgottenPasswordInterface {

    interface View{

        fun showEmailError()

        fun onSuccess()

        fun onFailure()

    }

    interface Presenter{

        fun checkEmail(email: String)

    }
}