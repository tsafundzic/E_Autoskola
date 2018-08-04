package com.tsafundzic.e_autoskola

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsafundzic.e_autoskola.models.Instructor
import com.tsafundzic.e_autoskola.presentation.ChatInterface

class InstructorAdapter(private val OnCandidateClickListener: ChatInterface.OnCandidateClickListener) : RecyclerView.Adapter<InstructorViewHolder>() {

    private var instructorss: MutableList<Instructor> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rc_item_friend, parent, false)
        return InstructorViewHolder(view, OnCandidateClickListener)
    }

    override fun getItemCount() = instructorss.size

    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
        val instructor = instructorss[position]
        holder.setInstructor(instructor)
    }

    fun setInstructors(instructors: ArrayList<Instructor>) {
        instructorss.clear()
        instructorss.addAll(instructors)
        notifyDataSetChanged()
    }

}