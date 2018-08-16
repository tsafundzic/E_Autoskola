package com.tsafundzic.e_autoskola.ui.instructorMain

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.extensions.getIntent
import android.util.Log
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.Detector
import android.view.SurfaceHolder
import android.view.WindowManager
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.tsafundzic.e_autoskola.presentation.QrCodeScannerInterface
import com.tsafundzic.e_autoskola.presentation.implementation.QrCodeScannerPresenterImpl
import kotlinx.android.synthetic.main.activity_qr_code_scanner.*
import java.io.IOException


class QrCodeScannerActivity : AppCompatActivity(), QrCodeScannerInterface.View, ActivityCompat.OnRequestPermissionsResultCallback {

    lateinit var presenter: QrCodeScannerInterface.Presenter

    companion object {
        fun getLaunchIntent(from: Context) = from.getIntent<QrCodeScannerActivity>().apply {
        }
    }

    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code_scanner)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        permissionStatus()
    }

    private fun permissionStatus() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            injectDependencies()
        else
            checkPermission()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 42)
        else
            permissionStatus()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                permissionStatus()
            else
                onBackPressed()
            return
        }
    }

    private fun injectDependencies() {
        presenter = QrCodeScannerPresenterImpl(this)
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
                presenter.handleDetections(detections.detectedItems)
            }
        })
    }

    override fun stopCamera() {
        cameraView.post({
            cameraSource?.stop()
        })
    }

    override fun onFailure() {
        showAlert()
    }

    private fun showAlert() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setMessage(getString(R.string.wrongQr))

        alertBuilder.setPositiveButton(getString(R.string.repeat), { dialogInterface, i ->
            dialogInterface.dismiss()
            onBackPressed()
        })

        alertBuilder.setNegativeButton(getString(R.string.cancel), { dialogInterface, i ->
            dialogInterface.dismiss()
            onBackPressed()
        })

        val alertDialog = alertBuilder.create()
        alertDialog.show()
    }

    override fun onSuccess(candidateId: String, candidateName: String) {
        returnBarcodeText(candidateId, candidateName)
    }

    private fun returnBarcodeText(candidateId: String, candidateName: String) {
        startActivity(InstructorMainActivity.getLaunchIntent(this, candidateId, candidateName))
        finish()
    }

}
