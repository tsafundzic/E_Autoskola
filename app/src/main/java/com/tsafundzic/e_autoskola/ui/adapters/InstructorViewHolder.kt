package com.tsafundzic.e_autoskola.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.presentation.ChatInterface
import kotlinx.android.synthetic.main.item_chat_user_info.view.*

class InstructorViewHolder(itemView: View, private inline val OnCandidateClickListener: ChatInterface.OnCandidateClickListener) : RecyclerView.ViewHolder(itemView) {
    fun setInstructor(instructor: Instructor) = with(itemView) {
        name.text = instructor.name
        message.text = instructor.email
        userInitial.text = instructor.name.substring(0, 1)

        itemView.setOnClickListener { OnCandidateClickListener.onInstructorClick(instructor) }

    }
}