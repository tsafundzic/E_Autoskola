package com.tsafundzic.e_autoskola

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.presentation.ChatInterface
import kotlinx.android.synthetic.main.rc_item_friend.view.*

class InstructorViewHolder(itemView: View, private inline val OnCandidateClickListener: ChatInterface.OnCandidateClickListener) : RecyclerView.ViewHolder(itemView) {
    fun setInstructor(instructor: Instructor) = with(itemView) {
        name.text = instructor.name
        message.text = instructor.email

        itemView.setOnClickListener { OnCandidateClickListener.onInstructorClick(instructor) }

    }
}