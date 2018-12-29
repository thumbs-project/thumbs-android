package com.thumbs.android.thumbsAndroid.presenter



abstract class BasePresenter<T>{
    abstract fun attachView(view : T)
    abstract fun removeView()
}