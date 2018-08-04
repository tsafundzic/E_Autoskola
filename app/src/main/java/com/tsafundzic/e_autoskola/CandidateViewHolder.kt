package com.tsafundzic.e_autoskola

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tsafundzic.e_autoskola.models.Candidate
import com.tsafundzic.e_autoskola.presentation.ChatInterface
import kotlinx.android.synthetic.main.rc_item_friend.view.*

class CandidateViewHolder(itemView: View, private inline val onInstructorClickListener: ChatInterface.onInstructorClickListener) : RecyclerView.ViewHolder(itemView) {
    fun setCandidate(candidate: Candidate) = with(itemView) {
        name.text = candidate.name
        message.text = candidate.email

        itemView.setOnClickListener { onInstructorClickListener.onCandidateClick(candidate) }
    }
}