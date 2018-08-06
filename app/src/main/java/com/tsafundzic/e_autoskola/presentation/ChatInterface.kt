package com.tsafundzic.e_autoskola.presentation

import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import java.util.*

interface ChatInterface {

    interface View {

        fun showInstructorItems(instructors: ArrayList<Instructor>)

        fun showCandidatesItems(candidates: ArrayList<Candidate>)

        fun startMessageActivity(candidate: Candidate, instructorId: String, instructorName: String)

        fun startMessageActivity(instructor: Instructor, candidateId: String, candidateName: String)

    }

    interface Presenter {

        fun getInstructorList()

        fun getCandidatesList()

        fun startChat(candidate: Candidate)

        fun startChat(instructor: Instructor)

    }

    interface onDatabaseListener {

        fun returnAssignedCandidates(candidates: ArrayList<Candidate>)

        fun returnInstructors(instructors: ArrayList<Instructor>)

        fun returnCurrentCandidateData(candidate: Candidate)

        fun returnCurrentInstructorData(instructor: Instructor)

    }

    interface onInstructorClickListener {

        fun onCandidateClick(candidate: Candidate)
        operator fun invoke(candidate: Candidate) {

        }
    }

    interface OnCandidateClickListener {

        fun onInstructorClick(instructor: Instructor)
        operator fun invoke(instructor: Candidate) {

        }
    }

}