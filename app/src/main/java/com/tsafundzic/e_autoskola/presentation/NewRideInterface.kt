package com.tsafundzic.e_autoskola.presentation

import android.os.Bundle

interface NewRideInterface {

    interface View {

        fun setCandidateText(candidateId: String, candidateName: String)

        fun setBundleError()

    }

    interface Presenter {

        fun checkBundleArguments(arguments: Bundle?)
    }
}