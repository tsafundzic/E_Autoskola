package com.tsafundzic.e_autoskola.ui.adapters

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.models.Message

class MessagesAdapter : RecyclerView.Adapter<MessagesViewHolder>() {

    private val VIEW_TYPE_ME = 1
    private val VIEW_TYPE_OTHER = 2

    private val messages = ArrayList<Message>()

    fun setMessages(listOfMessages: ArrayList<Message>) {
        messages.clear()
        messages.addAll(listOfMessages)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false)
        when (viewType) {
            VIEW_TYPE_ME -> {
                val viewChatMine = layoutInflater.inflate(R.layout.item_chat_mine, parent, false)
                return MessagesViewHolder(viewChatMine)
            }
            VIEW_TYPE_OTHER -> {
                val viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false)
                return MessagesViewHolder(viewChatOther)
            }
        }
        return MessagesViewHolder(viewChatOther)

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (TextUtils.equals(messages[position].senderUid, FirebaseAuth.getInstance().currentUser!!.uid)) {
            VIEW_TYPE_ME
        } else {
            VIEW_TYPE_OTHER
        }
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        if (TextUtils.equals(messages[position].senderUid, FirebaseAuth.getInstance().currentUser!!.uid)) {
            holder.configureMyChatViewHolder(messages[position])
        } else {
            holder.configureOtherChatViewHolder(messages[position])
        }
    }

}