package com.tsafundzic.e_autoskola.presentation

interface InstructorAccountInfoInterface {

    interface View{
        fun finishActivity()

    }

    interface Presenter: BasePresenter<View>{

        fun performSignOut()
    }
}