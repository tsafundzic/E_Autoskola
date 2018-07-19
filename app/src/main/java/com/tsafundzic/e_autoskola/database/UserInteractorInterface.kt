package com.tsafundzic.e_autoskola.database

import com.tsafundzic.e_autoskola.main.MainActivity

interface UserInteractorInterface {

    fun performFirebaseLogin(activity: MainActivity, email: String, password: String)


}