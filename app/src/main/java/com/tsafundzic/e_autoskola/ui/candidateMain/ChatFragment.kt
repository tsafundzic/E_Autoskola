package com.tsafundzic.e_autoskola.ui.candidateMain


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsafundzic.e_autoskola.ui.adapters.InstructorAdapter
import com.tsafundzic.e_autoskola.ui.MessagesActivity

import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.constants.CANDIDATE
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.presentation.ChatInterface
import com.tsafundzic.e_autoskola.presentation.implementation.ChatPresenterImpl
import java.util.*


class ChatFragment : Fragment(), ChatInterface.View, ChatInterface.OnCandidateClickListener {

    private lateinit var presenter: ChatInterface.Presenter
    private val adapter = InstructorAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        setAdapter(view)
        injectDependencies()
        return view
    }

    private fun injectDependencies() {
        presenter = ChatPresenterImpl(this)
        presenter.getInstructorList()
    }

    private fun setAdapter(view: View) {
        val listOfInstructors: RecyclerView = view.findViewById(R.id.usersList)
        listOfInstructors.setHasFixedSize(true)
        listOfInstructors.layoutManager = LinearLayoutManager(context)
        listOfInstructors.adapter = adapter
    }

    override fun startMessageActivity(instructor: Instructor, candidateId: String, candidateName: String) {
        startActivity(context?.let { MessagesActivity.getLaunchIntent(it, instructor.role, instructor.name, instructor.phone, candidateId, candidateName, CANDIDATE) })
    }

    override fun onInstructorClick(instructor: Instructor) {
        presenter.startChat(instructor)
    }

    override fun showInstructorItems(instructors: ArrayList<Instructor>) {
        adapter.setInstructors(instructors)
    }

    override fun startMessageActivity(candidate: Candidate, instructorId: String, instructorName: String) {}

    override fun showCandidatesItems(candidates: ArrayList<Candidate>) {}

}
