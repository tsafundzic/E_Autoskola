package com.tsafundzic.e_autoskola.ui.main

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.extensions.getIntent
import com.tsafundzic.e_autoskola.common.helpers.toast
import com.tsafundzic.e_autoskola.presentation.ForgottenPasswordInterface
import com.tsafundzic.e_autoskola.presentation.implementation.ForgottenPasswordPresenterImpl
import kotlinx.android.synthetic.main.activity_forgotten_password.*

class ForgottenPasswordActivity : AppCompatActivity(), ForgottenPasswordInterface.View {
    override fun onFailure() {
        toast(getString(R.string.wrongEmail))
    }

    override fun onSuccess() {
        toast(getString(R.string.successufulEmailSend))
        onBackPressed()
    }

    override fun showEmailError() {
        email.error = getString(R.string.wrongEmail)
    }

    companion object {
        fun getLaunchIntent(from: Context) = from.getIntent<ForgottenPasswordActivity>().apply {
        }
    }

    private lateinit var presenter: ForgottenPasswordInterface.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotten_password)

        injectDependencies()

        sendEmail.setOnClickListener { startForgottenPassword() }
    }

    private fun injectDependencies() {
        presenter = ForgottenPasswordPresenterImpl(this)
    }

    private fun startForgottenPassword() {
        presenter.checkEmail(email.text.toString())
    }
}
