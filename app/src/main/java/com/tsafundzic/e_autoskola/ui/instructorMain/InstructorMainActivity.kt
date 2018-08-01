package com.tsafundzic.e_autoskola.ui.instructorMain

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.constants.BARCODE
import com.tsafundzic.e_autoskola.common.constants.CANDIDATEID
import com.tsafundzic.e_autoskola.common.constants.CANDIDATENAME
import com.tsafundzic.e_autoskola.common.extensions.getIntent
import com.tsafundzic.e_autoskola.presentation.InstructorMainInterface
import com.tsafundzic.e_autoskola.presentation.implementation.InstructorMainPresenterImpl
import kotlinx.android.synthetic.main.activity_instructor_main.*

class InstructorMainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, InstructorMainInterface.View {

    companion object {
        fun getLaunchIntent(from: Context) = from.getIntent<InstructorMainActivity>().apply {
        }
        fun getLaunchIntent(from: Context, candidateId: String, candidateName: String) = from.getIntent<InstructorMainActivity>().apply {
            putExtra(CANDIDATEID, candidateId)
            putExtra(CANDIDATENAME, candidateName)
        }
    }

    private lateinit var presenter: InstructorMainInterface.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_main)

        injectDependencies()

        initBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        checkNewRideIntent()
    }

    private fun checkNewRideIntent() {
        presenter.checkIfHasExtras(intent.getStringExtra(CANDIDATEID), intent.getStringExtra(CANDIDATENAME))
    }

    override fun startAccountInfo() {
        presenter.callFragmentChange(R.id.instructorFragmentLayout, InstructorAccountInfo(), this)

        instructorBottomNav.menu.getItem(0).isChecked = true
    }

    override fun startNewRide(fragment: Fragment) {
        presenter.callFragmentChange(R.id.instructorFragmentLayout, fragment, this)

        instructorBottomNav.menu.getItem(1).isChecked = true
    }

    private fun injectDependencies() {
        presenter = InstructorMainPresenterImpl()
        presenter.setView(this)
    }

    private fun initBottomNavigation() {
        instructorBottomNav.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        presenter.checkNewFragment(item, R.id.instructorFragmentLayout, this)

        return true
    }


}
