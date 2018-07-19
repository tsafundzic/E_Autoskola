package com.tsafundzic.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.getIntent() = Intent(this, T::class.java)
