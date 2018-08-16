package com.tsafundzic.e_autoskola.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tsafundzic.e_autoskola.models.Message
import kotlinx.android.synthetic.main.item_chat_mine.view.*
import java.text.SimpleDateFormat
import java.util.*


class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun configureMyChatViewHolder(message: Message) = with(itemView) {
        chatMessage.text = message.message
        userInitial.text = message.receiver.substring(0, 1)

        val formatter = SimpleDateFormat("HH:mm - dd.MM.yyyy. ")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = message.timestamp.toLong()

        messageTime.text = formatter.format(calendar.time)

    }

    fun configureOtherChatViewHolder(message: Message) = with(itemView) {
        chatMessage.text = message.message
        userInitial.text = message.sender.substring(0, 1)

        val formatter = SimpleDateFormat("HH:mm - dd.MM.yyyy. ")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = message.timestamp.toLong()

        messageTime.text = formatter.format(calendar.time)

    }
}