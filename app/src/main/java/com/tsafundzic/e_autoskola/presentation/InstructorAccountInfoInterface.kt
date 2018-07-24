package com.tsafundzic.e_autoskola.presentation

import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.models.School

interface InstructorAccountInfoInterface {

    interface View{

        fun finishActivity()

        fun setUserData(instructor: Instructor)

        fun setImage(imageURL: String)

        fun setSchoolData(school: School)

    }

    interface Presenter{

        fun performSignOut()

        fun getInstructorData()

        fun getSchoolInfo(schoolId: String)
    }
}