package com.tsafundzic.e_autoskola.ui

import android.app.AlertDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.extensions.getIntent
import android.util.Log
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.Detector
import android.view.SurfaceHolder
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.tsafundzic.e_autoskola.ui.instructorMain.InstructorMainActivity
import kotlinx.android.synthetic.main.activity_qr_code_scanner.*
import java.io.IOException


class QrCodeScannerActivity : AppCompatActivity() {

    companion object {
        fun getLaunchIntent(from: Context) = from.getIntent<QrCodeScannerActivity>().apply {
        }
    }

    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code_scanner)

        setReader()
    }

    private fun setReader() {
        barcodeDetector = BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(24.0f)
                .build()
        startReading()
    }

    private fun startReading() {
        cameraView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource?.start(cameraView.holder)
                } catch (ie: IOException) {
                    Log.e("CAMERA SOURCE", ie.toString())
                } catch (securityException: SecurityException) {
                    Log.e("CAMERA SOURCE", securityException.message)
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource?.stop()
            }
        })

        handleDetections()
    }

    private fun handleDetections() {
        barcodeDetector?.setProcessor(object : Detector.Processor<Barcode> {

            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    cameraView.post({
                        cameraSource?.stop()
                        // displayData(barcodes.valueAt(0).displayValue)
                        returnBarcodeText(barcode = barcodes.valueAt(0).displayValue)
                    })
                }
            }
        })
    }

    /*
    private fun displayData(barcode: String) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setMessage(barcode)

        alertBuilder.setPositiveButton(getString(R.string.ok), { dialogInterface, i -> returnBarcodeText(barcode) })

        alertBuilder.setNegativeButton(getString(R.string.cancel), { dialogInterface, i ->
            dialogInterface.dismiss()
            startReading()
        })

        val alertDialog = alertBuilder.create()
        alertDialog.show()
    }
    */

    private fun returnBarcodeText(barcode: String) {

        startActivity(InstructorMainActivity.getLaunchIntent(this, barcode))
        finish()
    }


}