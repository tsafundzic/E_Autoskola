package com.tsafundzic.e_autoskola.presentation

import com.tsafundzic.e_autoskola.models.Message

interface MessagesInterface {

    interface View {

        fun showMessage(message: ArrayList<Message>)

        fun setMessageError()

    }

    interface Preesnter {

        fun getPreviousMessages(receiverId: String, senderId: String, identification: String)

        fun sendNewMessage(receiverId: String, receiverName: String, senderId: String, senderName: String, identification: String, messageText: String)

    }

    interface OnDatabaseListener {

        fun returnMessage(messages: ArrayList<Message>)

    }

}