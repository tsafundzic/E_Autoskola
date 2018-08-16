package com.tsafundzic.e_autoskola.interaction

import com.google.firebase.database.*
import com.tsafundzic.e_autoskola.common.constants.*
import com.tsafundzic.e_autoskola.models.Message
import com.tsafundzic.e_autoskola.presentation.MessagesInterface

class DatabaseMessageInteractorImpl(private var databaseListener: MessagesInterface.OnDatabaseListener) : DatabaseMessagesInteractorInterface {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun getDatabaseRef(): DatabaseReference {
        return myRef
    }

    override fun saveNewMessage(message: Message, candidateId: String, chatId: String) {
        val chatMessages = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                getDatabaseRef().child(USERS).child(candidateId).child(CHATS).child(chatId).child(message.timestamp).setValue(message)
                databaseListener.setNotification(message)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        getDatabaseRef().child(USERS).child(candidateId).child(CHATS).addListenerForSingleValueEvent(chatMessages)
    }



    override fun getMessages(receiverId: String, senderId: String, candidateId: String, chatId: String) {

        val queryRef = getDatabaseRef().child(USERS).child(candidateId).child(CHATS).child(chatId)

        queryRef.addChildEventListener(object : ChildEventListener {

            val messages = ArrayList<Message>()

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                val message = Message(dataSnapshot.child(SENDER).value.toString(),
                        dataSnapshot.child(RECEIVER).value.toString(),
                        dataSnapshot.child(SENDERID).value.toString(),
                        dataSnapshot.child(RECEIVERID).value.toString(),
                        dataSnapshot.child(MESSAGE).value.toString(),
                        dataSnapshot.child(TIMESTAMP).value.toString()
                )
                messages.add(message)

                databaseListener.returnMessage(messages)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

}