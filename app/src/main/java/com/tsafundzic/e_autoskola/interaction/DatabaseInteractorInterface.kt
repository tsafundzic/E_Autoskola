package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

interface DatabaseInteractorInterface {

    fun getDatabaseRef(): DatabaseReference

    fun getUserRole(user: FirebaseUser?)

    fun getInstructorData(uid: String)

    fun getInstructorImage(uid: String)

    fun getCandidateData(uid: String)

    fun getCandidateImage(uid: String)

    fun getSchoolInfoData(schoolId: String)

}