package com.tsafundzic.e_autoskola.models

data class Chat(
        var sender: String,
        var receiver: String,
        var senderUid: String,
        var receiverUid: String,
        var message: String,
        var timestamp: String
)