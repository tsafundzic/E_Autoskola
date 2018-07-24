package com.tsafundzic.e_autoskola.ui.candidateMain


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.models.School
import com.tsafundzic.e_autoskola.presentation.CandidateAccountInfoInterface
import com.tsafundzic.e_autoskola.presentation.implementation.CandidateAccountInfoImpl
import kotlinx.android.synthetic.main.fragment_candidate_account_info.*

class CandidateAccountInfo : Fragment(), View.OnClickListener, CandidateAccountInfoInterface.View {

    lateinit var imageV: ImageView
    private lateinit var presenter: CandidateAccountInfoInterface.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_candidate_account_info, container, false)
        val logout: Button = view.findViewById(R.id.logoutUser)
        imageV = view.findViewById(R.id.candidateQr)

        injectDependencies()
        logout.setOnClickListener(this)

        return view
    }

    private fun injectDependencies() {
        presenter = CandidateAccountInfoImpl(this)
        presenter.getCandidateData()
    }

    override fun onClick(p0: View?) {
        presenter.performSignOut()
    }

    override fun setImage(imageURL: String) {
        if (isAdded)
            Glide.with(this)
                    .load(imageURL)
                    .into(imageV)
    }

    override fun setUserData(candidate: Candidate) {
        presenter.getInstructorInfo(candidate.selectedInstructor)
        presenter.getSchoolInfo(candidate.selectedSchool)
        if (isAdded) {
            candidateName.text = candidate.name
            candidateEmail.text = candidate.email
            candidatePhone.text = candidate.phone
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

    override fun setInstructorData(instructor: Instructor) {
        if (isAdded) {
            instructorName.text = instructor.name
            instructorEmail.text = instructor.email
            instructorPhone.text = instructor.phone
        }
    }

    override fun finishActivity() {
        activity?.onBackPressed()
    }

}
