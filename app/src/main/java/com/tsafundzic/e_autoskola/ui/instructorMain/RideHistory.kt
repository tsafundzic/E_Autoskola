package com.tsafundzic.e_autoskola.ui.instructorMain


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner

import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.presentation.InstructorRideHistoryInterface
import com.tsafundzic.e_autoskola.presentation.implementation.InstructorRideHistoryPresenterImpl
import android.widget.ArrayAdapter


class RideHistory : Fragment(), InstructorRideHistoryInterface.View {


    private lateinit var presenter: InstructorRideHistoryInterface.Presenter
    lateinit var selectedCandidate: Spinner


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ride_history, container, false)

        injectDependencies()

        selectedCandidate = view.findViewById(R.id.candidates)

        val rideDetails: Button = view.findViewById(R.id.startRideDetails)
        rideDetails.setOnClickListener { getRideDetails() }

        return view
    }


    override fun setSpinner(candidates: ArrayList<String>, candidateAdapter: ArrayAdapter<String>) {
        if (isAdded)
            selectedCandidate.adapter = candidateAdapter
    }


    private fun getRideDetails() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun injectDependencies() {
        presenter = InstructorRideHistoryPresenterImpl(this)
        context?.let { presenter.getCandidates(it) }
    }


}
