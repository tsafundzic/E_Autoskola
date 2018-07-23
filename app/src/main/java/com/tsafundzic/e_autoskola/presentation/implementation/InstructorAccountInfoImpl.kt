package com.tsafundzic.e_autoskola.presentation.implementation

import com.tsafundzic.e_autoskola.interaction.UserInteractorImpl
import com.tsafundzic.e_autoskola.presentation.InstructorAccountInfoInterface

class InstructorAccountInfoImpl (private var userInteractor: UserInteractorImpl) : InstructorAccountInfoInterface.Presenter {
    override fun performSignOut() {
        userInteractor.signOutCurrentUser()
        view.finishActivity()
    }

    private lateinit var view: InstructorAccountInfoInterface.View

    override fun setView(view: InstructorAccountInfoInterface.View) {
        this.view = view
    }

}