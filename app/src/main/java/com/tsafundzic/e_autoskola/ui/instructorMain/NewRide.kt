package com.tsafundzic.e_autoskola.ui.instructorMain


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.ui.QrCodeScannerActivity
import com.tsafundzic.e_autoskola.presentation.NewRideInterface
import com.tsafundzic.e_autoskola.presentation.implementation.NewRidePresenterImpl
import com.tsafundzic.e_autoskola.ui.RideActivity
import kotlinx.android.synthetic.main.fragment_new_ride.*


class NewRide : Fragment(), NewRideInterface.View {


    private lateinit var presenter: NewRideInterface.Presenter
    lateinit var selectedCandidateName: TextView
    lateinit var startRide: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_new_ride, container, false)

        injectDependencies()

        val cameraScanner: Button = view.findViewById(R.id.scanCandidate)
        cameraScanner.setOnClickListener { startCameraScaner() }

        selectedCandidateName = view.findViewById(R.id.scannedCandidate)

        startRide = view.findViewById(R.id.startRide)
        startRide.isEnabled = false

        return view
    }

    override fun onResume() {
        super.onResume()
        getBundleExtras()

    }

    private fun injectDependencies() {
        presenter = NewRidePresenterImpl(this)
    }

    private fun getBundleExtras() {
        presenter.checkBundleArguments(arguments)
    }

    override fun setCandidateText(candidateId: String, candidateName: String) {
        if (isAdded)
            selectedCandidateName.text = candidateName
        startRide.isEnabled = true
        startRide.setOnClickListener { startRideActivity(candidateId, candidateName) }
    }

    private fun startRideActivity(candidateId: String, candidateName: String) {
        startActivity(context?.let { RideActivity.getLaunchIntent(it, candidateId, candidateName) })
    }

    override fun setBundleError() {
        if (isAdded)
            scannedCandidate.text = getString(R.string.notSelected)
    }

    private fun startCameraScaner() {
        startActivity(context?.let { QrCodeScannerActivity.getLaunchIntent(it) })
    }


}
