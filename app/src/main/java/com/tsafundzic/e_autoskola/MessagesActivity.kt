package com.tsafundzic.e_autoskola

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.tsafundzic.e_autoskola.common.constants.ID
import com.tsafundzic.e_autoskola.common.constants.NAME
import com.tsafundzic.e_autoskola.common.extensions.getIntent
import kotlinx.android.synthetic.main.activity_messages.*

class MessagesActivity : AppCompatActivity() {
    companion object {
        fun getLaunchIntent(from: Context, candidateId: String, candidateName: String) = from.getIntent<MessagesActivity>().apply {
            putExtra(ID, candidateId)
            putExtra(NAME, candidateName)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        connectedUser.text = intent.getStringExtra(NAME)
    }
}
