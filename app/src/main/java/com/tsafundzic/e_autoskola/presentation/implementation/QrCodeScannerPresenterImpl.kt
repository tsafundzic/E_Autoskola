package com.tsafundzic.e_autoskola.presentation.implementation

import android.util.SparseArray
import com.google.android.gms.vision.barcode.Barcode

import com.tsafundzic.e_autoskola.interaction.DatabaseQrScannerInteractorImpl
import com.tsafundzic.e_autoskola.presentation.QrCodeScannerInterface

class QrCodeScannerPresenterImpl(private var view: QrCodeScannerInterface.View) : QrCodeScannerInterface.Presenter, QrCodeScannerInterface.OnDatabaseListener {

    private var databaseInteractor: DatabaseQrScannerInteractorImpl = DatabaseQrScannerInteractorImpl(this)

    override fun handleDetections(barcodeDetector: SparseArray<Barcode>) {

        if (barcodeDetector.size() != 0) {
            view.stopCamera()
            databaseInteractor.getCandidateNAme(barcodeDetector.valueAt(0).displayValue)
        }
    }

    override fun returnDatabaseError() {
        view.onFailure()
    }

    override fun returnCandidateIdAndName(candidateId: String, candidateName: String) {
        view.onSuccess(candidateId, candidateName)
    }
}