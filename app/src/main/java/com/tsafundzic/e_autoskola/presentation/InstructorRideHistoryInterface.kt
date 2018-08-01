package com.tsafundzic.e_autoskola.presentation

import android.content.Context
import android.widget.ArrayAdapter

interface InstructorRideHistoryInterface {

    interface View {

        fun setSpinner(candidates: ArrayList<String>, candidateAdapter: ArrayAdapter<String>)

    }

    interface Presenter {

        fun getCandidates(context: Context)

    }

    interface onDatabaseListener {

        fun returnCandidates(candidates: ArrayList<String>, context: Context)

    }
}