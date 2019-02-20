package com.thumbs.android.thumbsAndroid.ui.menu

import android.util.Log
import com.thumbs.android.thumbsAndroid.repositories.UserEventRepository

class MenuPresenter(val menuRepository: UserEventRepository) :
    MenuContract.UserActionListerner {

    var menuView: MenuContract.MenuView? = null

    override fun postUserEvent() {
        Log.d("AAA", "GGGG")
    }
}
