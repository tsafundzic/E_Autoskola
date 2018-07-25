package com.tsafundzic.e_autoskola.ui

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tsafundzic.e_autoskola.R
import com.tsafundzic.e_autoskola.common.constants.CANDIDATEID
import com.tsafundzic.e_autoskola.common.extensions.getIntent
import kotlinx.android.synthetic.main.activity_ride.*

class RideActivity : AppCompatActivity() {

    companion object {
        fun getLaunchIntent(from: Context, candidateId: String) = from.getIntent<RideActivity>().apply {
            putExtra(CANDIDATEID, candidateId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride)

        candidateId.text = intent.getStringExtra(CANDIDATEID)
        timeCounter.start()
    }
}
