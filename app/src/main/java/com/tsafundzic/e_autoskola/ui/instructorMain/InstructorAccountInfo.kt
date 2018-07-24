package com.tsafundzic.e_autoskola.ui.instructorMain


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.models.School
import com.tsafundzic.e_autoskola.presentation.InstructorAccountInfoInterface
import com.tsafundzic.e_autoskola.presentation.implementation.InstructorAccountInfoImpl
import kotlinx.android.synthetic.main.fragment_instructor_account_info.*


class InstructorAccountInfo : Fragment(), View.OnClickListener, InstructorAccountInfoInterface.View {

    lateinit var imageV: ImageView
    private lateinit var presenter: InstructorAccountInfoInterface.Presenter

    override fun onClick(p0: View?) {
        presenter.performSignOut()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_instructor_account_info, container, false)
        val logout: Button = view.findViewById(R.id.logoutUser)
        imageV = view.findViewById(R.id.instructorQr)

        injectDependencies()

        logout.setOnClickListener(this)
        return view
    }

    private fun injectDependencies() {
        presenter = InstructorAccountInfoImpl(this)
        presenter.getInstructorData()
    }

    override fun setImage(imageURL: String) {
        if (isAdded)
            Glide.with(this)
                    .load(imageURL)
                    .into(imageV)
    }

    override fun setUserData(instructor: Instructor) {
        presenter.getSchoolInfo(instructor.selectedSchool)

        if (isAdded) {
            instructorName.text = instructor.name
            instructorEmail.text = instructor.email
            instructorPhone.text = instructor.phone
        }
    }

    override fun setSchoolData(school: School) {
        if (isAdded) {
            selectedSchool.text = school.name
            schoolEmail.text = school.email
            schoolAddress.text = school.address
            schoolCity.text = school.city
            schoolPhone.text = school.phone
            schoolIban.text = school.iban
            schoolOib.text = school.oib
        }
    }

    override fun finishActivity() {
        activity?.onBackPressed()
    }

}
