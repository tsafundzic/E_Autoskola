package com.tsafundzic.e_autoskola.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tsafundzic.common.helpers.toast
import com.tsafundzic.e_autoskola.CandidateMainActivity
import com.tsafundzic.e_autoskola.InstructorMainActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.database.*


class MainActivity : AppCompatActivity(), MainInterface.View {

    private lateinit var presenter: MainInterface.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependencies()

        email_sign_in_button.setOnClickListener {
            presenter.signIn(this, field_email.text.toString(), field_password.text.toString())
        }
    }

    private fun injectDependencies() {
        val databaseInteractor = DatabaseInteractorImpl()
        val userInteractor = UserInteractorImpl()
        presenter = MainPresenterImpl(databaseInteractor, userInteractor)
        presenter.setView(this)
    }

    public override fun onStart() {
        super.onStart()

        presenter.checkForLoggedUser()

    }

    override fun startCandidateMainActivity() {
        startActivity(CandidateMainActivity.getLaunchIntent(this))
    }

    override fun startInstructorMainActivity() {
        startActivity(InstructorMainActivity.getLaunchIntent(this))
    }

    override fun errorNotHaveAutorisation() {
        toast(getString(R.string.NotHavingAutorisation))
    }

    override fun errorWrongUserInformation() {
        toast(getString(R.string.wrongEmailOrPassword))
    }

}
