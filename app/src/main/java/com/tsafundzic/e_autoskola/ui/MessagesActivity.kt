package com.tsafundzic.e_autoskola.ui

import android.Manifest
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.tsafundzic.e_autoskola.common.constants.*

import com.tsafundzic.e_autoskola.common.extensions.getIntent
import com.tsafundzic.e_autoskola.models.Message
import com.tsafundzic.e_autoskola.presentation.MessagesInterface
import com.tsafundzic.e_autoskola.presentation.implementation.MessagesPresenterImpl
import com.tsafundzic.e_autoskola.ui.adapters.MessagesAdapter
import kotlinx.android.synthetic.main.activity_messages.*
import com.tsafundzic.e_autoskola.R
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class MessagesActivity : AppCompatActivity(), MessagesInterface.View, ActivityCompat.OnRequestPermissionsResultCallback {

    companion object {
        fun getLaunchIntent(from: Context, receiverId: String, receiverName: String, receiverPhone: String, senderId: String, senderName: String, identification: String) = from.getIntent<MessagesActivity>().apply {
            putExtra(RECEIVERID, receiverId)
            putExtra(RECEIVERNAME, receiverName)
            putExtra(PHONE, receiverPhone)
            putExtra(SENDERID, senderId)
            putExtra(SENDERNAME, senderName)
            putExtra(IDENTIFICATION, identification)
        }
    }

    private lateinit var presenter: MessagesInterface.Preesnter

    private val adapter = MessagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        injectDependencies()
        setAdapter()
        connectedUser.text = intent.getStringExtra(RECEIVERNAME)
        sendMessage.setOnClickListener { sendNewMessage() }
        back.setOnClickListener { onBackPressed() }
        call.setOnClickListener { callPerson() }

    }

    override fun setNotification(message: Message) {
    }

    private fun sendNewMessage() {
        presenter.sendNewMessage(intent.getStringExtra(RECEIVERID), intent.getStringExtra(RECEIVERNAME), intent.getStringExtra(SENDERID), intent.getStringExtra(SENDERNAME), intent.getStringExtra(IDENTIFICATION), textMessage.text.toString())
    }

    private fun injectDependencies() {
        presenter = MessagesPresenterImpl(this)
        presenter.getPreviousMessages(intent.getStringExtra(RECEIVERID), intent.getStringExtra(SENDERID), intent.getStringExtra(IDENTIFICATION))
    }

    private fun setAdapter() {
        val listOfMessages: RecyclerView = findViewById(R.id.messagesList)
        listOfMessages.setHasFixedSize(true)
        listOfMessages.layoutManager = LinearLayoutManager(this)
        (listOfMessages.layoutManager as LinearLayoutManager).stackFromEnd = true
        listOfMessages.adapter = adapter
    }

    override fun setMessageError() {
        textMessage.error = getString(R.string.emptyMessage)
    }

    override fun showMessage(message: ArrayList<Message>) {
        textMessage.setText("")
        adapter.setMessages(message)
        setAdapter()
    }


    private fun callPerson() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + intent.getStringExtra(PHONE))
            startActivity(callIntent)
        } else {
            checkPermission()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 42)
        else
            callPerson()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                callPerson()
            return
        }
    }

}
