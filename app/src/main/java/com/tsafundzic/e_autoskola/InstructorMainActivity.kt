package com.tsafundzic.e_autoskola

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tsafundzic.common.extensions.getIntent

class InstructorMainActivity : AppCompatActivity() {

    companion object {
        fun getLaunchIntent(from: Context) = from.getIntent<InstructorMainActivity>().apply {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_main)
    }
}
