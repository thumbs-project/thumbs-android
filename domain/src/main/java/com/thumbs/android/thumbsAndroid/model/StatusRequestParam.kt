package com.thumbs.android.thumbsAndroid.model

import com.google.gson.annotations.SerializedName

data class StatusRequestParam(
  @SerializedName("request_id")
  val requestId: Long,
  val event: String,
  val data: Any = Any()
)