package com.tsafundzic.e_autoskola.common.helpers

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast
import android.support.v4.app.FragmentActivity
import android.app.Activity
import android.util.Patterns
import com.tsafundzic.e_autoskola.R


fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()


 fun changeFragments(container: Int, fragment: Fragment, activity: Activity) {
    val fragmentTransaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
     fragmentTransaction.setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
    fragmentTransaction.replace(container, fragment)
    fragmentTransaction.commit()
}


fun String.isValidEmail(): Boolean
        = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()