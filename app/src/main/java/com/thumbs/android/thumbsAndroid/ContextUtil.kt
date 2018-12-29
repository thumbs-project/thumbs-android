package com.thumbs.android.thumbsAndroid.ui

import android.content.Context
import android.widget.Toast


fun Context?.showToastMass(msg : String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}

fun Context?.sho