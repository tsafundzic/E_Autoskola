package com.tsafundzic.e_autoskola.presentation.implementation

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.constants.ID
import com.tsafundzic.e_autoskola.common.constants.NAME
import com.tsafundzic.e_autoskola.common.helpers.changeFragments
import com.tsafundzic.e_autoskola.presentation.InstructorMainInterface
import com.tsafundzic.e_autoskola.ui.instructorMain.InstructorAccountInfo
import com.tsafundzic.e_autoskola.ui.instructorMain.InstructorChatFragment
import com.tsafundzic.e_autoskola.ui.instructorMain.NewRide
import com.tsafundzic.e_autoskola.ui.instructorMain.RideHistory

class InstructorMainPresenterImpl : InstructorMainInterface.Presenter {


    private lateinit var view: InstructorMainInterface.View

    override fun setView(view: InstructorMainInterface.View) {
        this.view = view
    }

    override fun checkIfHasExtras(candidateId: String?, candidateName: String?) {
        if (candidateId != null) {
            val fragment = NewRide()
            val args = Bundle()
            args.putString(ID, candidateId)
            args.putString(NAME, candidateName)
            fragment.arguments = args
            view.startNewRide(fragment)
        } else {
            view.startAccountInfo()
        }
    }

    override fun checkNewFragment(item: MenuItem, fragmentLayout: Int, activity: Activity) {
        when (item.itemId) {
            R.id.accountInfo -> {
                changeFragments(fragmentLayout, InstructorAccountInfo(), activity)
            }
            R.id.newRide -> {
                changeFragments(fragmentLayout, NewRide(), activity)
            }
            R.id.rideHistory -> {
                changeFragments(fragmentLayout, RideHistory(), activity)
            }
            R.id.chat -> {
                changeFragments(fragmentLayout, InstructorChatFragment(), activity)
            }

        }
    }

    override fun callFragmentChange(fragmentLayout: Int, fragment: Fragment, activity: Activity) {
        changeFragments(fragmentLayout, fragment, activity)
    }
}