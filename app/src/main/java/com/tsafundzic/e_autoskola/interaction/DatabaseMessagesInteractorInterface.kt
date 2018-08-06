package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.database.DatabaseReference
import com.tsafundzic.e_autoskola.models.Message

interface DatabaseMessagesInteractorInterface {

    fun getDatabaseRef(): DatabaseReference

    fun getMessages(receiverId: String, senderId: String, candidateId: String, chatId: String)

    fun saveNewMessage(message: Message, candidateId: String, chatId: String)


}