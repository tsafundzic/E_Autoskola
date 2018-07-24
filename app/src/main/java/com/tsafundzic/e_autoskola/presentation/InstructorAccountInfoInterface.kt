package com.tsafundzic.e_autoskola.presentation

import com.tsafundzic.e_autoskola.models.Instructor

interface InstructorAccountInfoInterface {

    interface View{

        fun finishActivity()

        fun setUserData(instructor: Instructor)

        fun setImage(imageURL: String)

    }

    interface Presenter{

        fun performSignOut()

        fun getInstructorData()
    }
}