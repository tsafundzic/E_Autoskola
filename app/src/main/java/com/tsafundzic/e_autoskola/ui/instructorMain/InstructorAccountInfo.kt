package com.tsafundzic.e_autoskola.ui.instructorMain


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.presentation.InstructorAccountInfoInterface
import com.tsafundzic.e_autoskola.presentation.implementation.InstructorAccountInfoImpl


class InstructorAccountInfo : Fragment(), View.OnClickListener, InstructorAccountInfoInterface.View {
    override fun finishActivity() {
        activity?.onBackPressed()
    }

    private lateinit var presenter: InstructorAccountInfoInterface.Presenter


    override fun onClick(p0: View?) {
       presenter.performSignOut()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_instructor_account_info, container, false)
        val btn: Button = view.findViewById(R.id.logoutUser)
        btn.setOnClickListener(this)
        injectDependencies()
        return view
    }

    private fun injectDependencies() {
        val userInteractor = UserInteractorImpl()
        presenter = InstructorAccountInfoImpl(userInteractor)
        (presenter as InstructorAccountInfoImpl).setView(this)
    }

}
