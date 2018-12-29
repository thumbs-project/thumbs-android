package com.thumbs.android.thumbsAndroid.modules.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConnector {
  val authToken = "1"
  val baseUrl = "http://api.thumbs.noverish.me"

  val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    })
    .addInterceptor { chain ->
      val request = chain.request().newBuilder().addHeader("Authorization", "1").build()
      chain.proceed(request)
    }
    .build()

  fun <T> createRetrofit(api: Class<T>): T {
    return Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .build()
      .create(api)
  }
}
