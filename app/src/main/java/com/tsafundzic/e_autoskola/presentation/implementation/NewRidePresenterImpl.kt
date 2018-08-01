package com.tsafundzic.e_autoskola.presentation.implementation

import android.os.Bundle
import com.tsafundzic.e_autoskola.common.constants.ID
import com.tsafundzic.e_autoskola.common.constants.NAME
import com.tsafundzic.e_autoskola.presentation.NewRideInterface

class NewRidePresenterImpl(private var view: NewRideInterface.View) : NewRideInterface.Presenter {


    override fun checkBundleArguments(arguments: Bundle?) {
        if (arguments?.getString(NAME) != null) {
            view.setCandidateText(arguments.getString(ID), arguments.getString(NAME))
        }
    }
}