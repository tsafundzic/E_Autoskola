package com.tsafundzic.e_autoskola.ui.instructorMain

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.extensions.getIntent
import com.tsafundzic.e_autoskola.presentation.InstructorMainInterface
import com.tsafundzic.e_autoskola.presentation.implementation.InstructorMainPresenterImpl
import kotlinx.android.synthetic.main.activity_instructor_main.*

class InstructorMainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, InstructorMainInterface.View {

    private lateinit var presenter: InstructorMainInterface.Presenter

    companion object {
        fun getLaunchIntent(from: Context) = from.getIntent<InstructorMainActivity>().apply {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_main)

        injectDependencies()
        initBottomNavigation()
        presenter.callFragmentChange(R.id.instructorFragmentLayout, InstructorAccountInfo(), this)

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
