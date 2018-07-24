package com.tsafundzic.e_autoskola.ui.main

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tsafundzic.e_autoskola.common.helpers.toast
import com.tsafundzic.e_autoskola.ui.candidateMain.CandidateMainActivity
import com.tsafundzic.e_autoskola.ui.instructorMain.InstructorMainActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.presentation.MainInterface
import com.tsafundzic.e_autoskola.presentation.implementation.MainPresenterImpl


class MainActivity : AppCompatActivity(), MainInterface.View {

    private lateinit var presenter: MainInterface.Presenter
    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()
        button_login.setOnClickListener { startSignIn() }
    }

    private fun injectDependencies() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage(getString(R.string.autorization))
        presenter = MainPresenterImpl(this)
    }

    public override fun onStart() {
        super.onStart()
        mProgressDialog.show()
        presenter.checkForLoggedUser()
    }

    private fun startSignIn() {
        mProgressDialog.show()
        presenter.signIn(email_login.text.toString(), password_login.text.toString())
    }

    override fun startCandidateMainActivity() {
        mProgressDialog.dismiss()
        startActivity(CandidateMainActivity.getLaunchIntent(this))
    }

    override fun startInstructorMainActivity() {
        //mProgressDialog.dismiss()
        startActivity(InstructorMainActivity.getLaunchIntent(this))
    }

    override fun errorNotHaveAuthorisation() {
        mProgressDialog.dismiss()
        toast(getString(R.string.NotHavingAutorisation))
    }

    override fun errorWrongUserInformation() {
        mProgressDialog.dismiss()
        toast(getString(R.string.wrongEmailOrPassword))
    }

    override fun setPasswordError() {
        mProgressDialog.dismiss()
        password_login.error = getString(R.string.passwordMustContain)
    }

    override fun setEmailError() {
        mProgressDialog.dismiss()
        email_login.error = getString(R.string.wrongEmail)
    }

    override fun turnOffProgress() {
        mProgressDialog.dismiss()
    }


}