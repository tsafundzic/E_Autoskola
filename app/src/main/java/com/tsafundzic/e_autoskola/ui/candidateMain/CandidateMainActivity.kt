package com.tsafundzic.e_autoskola.ui.candidateMain

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.extensions.getIntent
import com.tsafundzic.e_autoskola.presentation.CandidateMainInterface
import com.tsafundzic.e_autoskola.presentation.implementation.CandidateMainPresenterImpl
import kotlinx.android.synthetic.main.activity_candidate_main.*

class CandidateMainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, CandidateMainInterface.View{

    companion object {
        fun getLaunchIntent(from: Context) = from.getIntent<CandidateMainActivity>().apply {
        }
    }

    private lateinit var presenter: CandidateMainInterface.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_main)

        injectDependencies()
        initBottomNavigation()
        presenter.callFragmentChange(R.id.candidateFragmentLayout, CandidateAccountInfo(), this)
    }

    private fun injectDependencies() {
        presenter = CandidateMainPresenterImpl()
        presenter.setView(this)
    }

    private fun initBottomNavigation() {
        candidateBottomNav.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        presenter.checkNewFragment(item, R.id.candidateFragmentLayout, this)
        return true
    }


}
