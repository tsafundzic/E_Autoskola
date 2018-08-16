package com.tsafundzic.e_autoskola.presentation.implementation

import android.support.v4.app.NotificationCompat
import com.tsafundzic.e_autoskola.common.constants.CANDIDATE
import com.tsafundzic.e_autoskola.interaction.DatabaseMessageInteractorImpl
import com.tsafundzic.e_autoskola.models.Message
import com.tsafundzic.e_autoskola.presentation.MessagesInterface
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage





class MessagesPresenterImpl(private var view: MessagesInterface.View) : MessagesInterface.Preesnter, MessagesInterface.OnDatabaseListener {
    override fun setNotification(message: Message) {
        view.setNotification(message)



    }

    override fun sendNewMessage(receiverId: String, receiverName: String, senderId: String, senderName: String, identification: String, messageText: String) {
        if (messageText.isEmpty()) {
            view.setMessageError()
        } else {
            val message = Message(senderName, receiverName, senderId, receiverId, messageText, System.currentTimeMillis().toString())
            if (identification == CANDIDATE)
                databaseInteractor.saveNewMessage(message, senderId, receiverId + "_" + senderId)
            else
                databaseInteractor.saveNewMessage(message, receiverId, senderId + "_" + receiverId)
        }
    }

    override fun returnMessage(messages: ArrayList<Message>) {
        view.showMessage(messages)
    }

    private var databaseInteractor = DatabaseMessageInteractorImpl(this)

    override fun getPreviousMessages(receiverId: String, senderId: String, identification: String) {
        if (identification == CANDIDATE)
            databaseInteractor.getMessages(receiverId, senderId, senderId, receiverId + "_" + senderId)
        else
            databaseInteractor.getMessages(receiverId, senderId, receiverId, senderId + "_" + receiverId)
    }


}