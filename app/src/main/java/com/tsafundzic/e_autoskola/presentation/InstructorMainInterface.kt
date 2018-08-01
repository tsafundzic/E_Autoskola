package com.tsafundzic.e_autoskola.presentation

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem

interface InstructorMainInterface {
    interface View {

        fun startAccountInfo()

        fun startNewRide(fragment: Fragment)

    }

    interface Presenter : BasePresenter<View> {

        fun checkNewFragment(item: MenuItem, fragmentLayout: Int,  activity: Activity)

        fun callFragmentChange(fragmentLayout: Int, fragment: Fragment, activity: Activity)

        fun checkIfHasExtras(candidateId: String?,candidateName: String?)

    }

}