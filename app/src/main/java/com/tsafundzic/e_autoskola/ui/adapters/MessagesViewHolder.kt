package com.tsafundzic.e_autoskola.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tsafundzic.e_autoskola.models.Message
import kotlinx.android.synthetic.main.item_chat_mine.view.*

class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun configureMyChatViewHolder( message: Message) = with(itemView) {
        chatMessage.text = message.message
        userInitial.text = message.sender.substring(0, 1)

    }

    fun configureOtherChatViewHolder( message: Message) = with(itemView) {
        chatMessage.text = message.message
        userInitial.text = message.sender.substring(0, 1)
    }
}