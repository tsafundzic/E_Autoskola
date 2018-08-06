package com.tsafundzic.e_autoskola.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.presentation.ChatInterface

class CandidateAdapter(private val onInstructorClickListener: ChatInterface.onInstructorClickListener) : RecyclerView.Adapter<CandidateViewHolder>() {

    private var candidates: MutableList<Candidate> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_user_info, parent, false)
        return CandidateViewHolder(view, onInstructorClickListener)
    }

    override fun getItemCount() = candidates.size

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        val candidate = candidates[position]
        holder.setCandidate(candidate)
    }

    fun setCandidates(candidatesList: ArrayList<Candidate>) {
        candidates.clear()
        candidates.addAll(candidatesList)
        notifyDataSetChanged()
    }

}