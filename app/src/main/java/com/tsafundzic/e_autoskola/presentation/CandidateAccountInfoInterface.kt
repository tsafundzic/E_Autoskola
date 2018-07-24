package com.tsafundzic.e_autoskola.presentation

import com.tsafundzic.e_autoskola.models.Candidate

interface CandidateAccountInfoInterface {

    interface View{

        fun finishActivity()

        fun setUserData(candidate: Candidate)

        fun setImage(imageURL: String)

    }

    interface Presenter{

        fun performSignOut()

        fun getCandidateData()
    }
}