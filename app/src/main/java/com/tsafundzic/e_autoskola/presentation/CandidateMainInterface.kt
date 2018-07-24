package com.tsafundzic.e_autoskola.presentation

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.MenuItem

interface CandidateMainInterface {
    interface View {

    }

    interface Presenter : BasePresenter<View> {

        fun checkNewFragment(item: MenuItem, fragmentLayout: Int, activity: Activity)

        fun callFragmentChange(fragmentLayout: Int, fragment: Fragment, activity: Activity)
    }

}