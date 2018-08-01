package com.tsafundzic.e_autoskola.presentation.implementation

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.helpers.changeFragments
import com.tsafundzic.e_autoskola.presentation.CandidateMainInterface
import com.tsafundzic.e_autoskola.ui.ChatFragment
import com.tsafundzic.e_autoskola.ui.candidateMain.CandidateAccountInfo
import com.tsafundzic.e_autoskola.ui.candidateMain.CandidateRideHistory

class CandidateMainPresenterImpl : CandidateMainInterface.Presenter {
    private lateinit var view: CandidateMainInterface.View

    override fun setView(view: CandidateMainInterface.View) {
        this.view = view
    }

    override fun checkNewFragment(item: MenuItem, fragmentLayout: Int, activity: Activity) {
        when (item.itemId) {
            R.id.accountInfo -> {
                changeFragments(fragmentLayout, CandidateAccountInfo(), activity)
            }
            R.id.rideHistory -> {
                changeFragments(fragmentLayout, CandidateRideHistory(), activity)
            }
            R.id.chat -> {
                changeFragments(fragmentLayout, ChatFragment(), activity)
            }
        }
    }

    override fun callFragmentChange(fragmentLayout: Int, fragment: Fragment, activity: Activity) {
        changeFragments(fragmentLayout, fragment, activity)
    }
}