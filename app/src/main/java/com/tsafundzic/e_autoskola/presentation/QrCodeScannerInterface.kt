package com.tsafundzic.e_autoskola.presentation

import android.util.SparseArray
import com.google.android.gms.vision.barcode.Barcode

interface QrCodeScannerInterface {

    interface View {
        fun onSuccess(candidateId: String, candidateName: String)
        fun onFailure()
        fun stopCamera()

    }

    interface Presenter {

        fun handleDetections(barcodeDetector: SparseArray<Barcode>)

    }

    interface OnDatabaseListener {

        fun returnCandidateIdAndName(candidateId: String, candidateName: String)

        fun returnDatabaseError()

    }

}