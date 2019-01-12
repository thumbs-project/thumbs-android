package com.thumbs.android.thumbsAndroid.ui.base




interface BasePresenter<T>{
    fun attachView(view : T)
    fun removeView()
}
