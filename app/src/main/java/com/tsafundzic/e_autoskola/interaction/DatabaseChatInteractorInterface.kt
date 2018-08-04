package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.database.DatabaseReference
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor

interface DatabaseChatInteractorInterface {

    fun getDatabaseRef(): DatabaseReference

    fun getCurrentInstructorData(userUid: String)

    fun getCurrentCandidateData(userUid: String)

    fun getSelectedInstructor(candidate: Candidate)

    fun getAssignedCandidates(instructor: Instructor)


}