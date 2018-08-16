package com.tsafundzic.e_autoskola.ui.instructorMain


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsafundzic.e_autoskola.ui.adapters.CandidateAdapter
import com.tsafundzic.e_autoskola.ui.MessagesActivity

import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.constants.INSTRUCTOR
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.presentation.ChatInterface
import com.tsafundzic.e_autoskola.presentation.implementation.ChatPresenterImpl
import java.util.*


class InstructorChatFragment : Fragment(), ChatInterface.View, ChatInterface.onInstructorClickListener {

    private lateinit var presenter: ChatInterface.Presenter
    private val adapter = CandidateAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        setAdapter(view)
        injectDependencies()

        return view
    }

    private fun injectDependencies() {
        presenter = ChatPresenterImpl(this)
        presenter.getCandidatesList()
    }

    private fun setAdapter(view: View) {
        val listOfCandidates: RecyclerView = view.findViewById(R.id.usersList)
        listOfCandidates.setHasFixedSize(true)
        listOfCandidates.layoutManager = LinearLayoutManager(context)
        listOfCandidates.adapter = adapter
    }

    override fun showCandidatesItems(candidates: ArrayList<Candidate>) {
        adapter.setCandidates(candidates)
    }

    override fun startMessageActivity(candidate: Candidate, instructorId: String, instructorName: String) {
        startActivity(context?.let { MessagesActivity.getLaunchIntent(it, candidate.category, candidate.name, candidate.phone, instructorId, instructorName, INSTRUCTOR) })
    }

    override fun onCandidateClick(candidate: Candidate) {
        presenter.startChat(candidate)
    }

    override fun startMessageActivity(instructor: Instructor, candidateId: String, candidateName: String) {}

    override fun showInstructorItems(instructors: ArrayList<Instructor>) {}

}
