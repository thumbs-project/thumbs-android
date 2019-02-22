package com.thumbs.android.thumbsAndroid.model

data class UserAction(
    val requestId: Long,
    val event: String,
    val payload: Any = Any()
)