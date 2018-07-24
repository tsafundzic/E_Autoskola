package com.tsafundzic.e_autoskola.presentation

import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.models.School

interface CandidateAccountInfoInterface {

    interface View{

        fun finishActivity()

        fun setUserData(candidate: Candidate)

        fun setImage(imageURL: String)

        fun setInstructorData(instructor: Instructor)

        fun setSchoolData(school: School)

    }

    interface Presenter{

        fun performSignOut()

        fun getCandidateData()

        fun getInstructorInfo(instructorUid: String)

        fun getSchoolInfo(schoolId: String)
    }
}