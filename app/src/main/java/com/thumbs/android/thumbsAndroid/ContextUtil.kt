package com.thumbs.android.thumbsAndroid

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast

fun Context?.showToastMessage(strId : Int){
    Toast.makeText(this, strId, Toast.LENGTH_SHORT).show()
}

fun Context?.showToastMessageString(str : String){
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

fun Activity.showToastMessage(strId: Int){
    Toast.makeText(this.application,strId,Toast.LENGTH_SHORT).show()
}

fun Activity.showToastMessageString(str: String){
    Toast.makeText(this.application,str,Toast.LENGTH_SHORT).show()
}

fun Fragment.showToastMessage(strId : Int){
    Toast.makeText(this.context,strId,Toast.LENGTH_SHORT).show()
}

fun Fragment.showToastMessageString(str : Int){
    Toast.makeText(this.context,str,Toast.LENGTH_SHORT).show()
}

fun Context.dpToPixel(dp : Int)  = dp * resources.displayMetrics.density

fun Context.pixelToDp(pixel : Float) = pixel / resources.displayMetrics.density
