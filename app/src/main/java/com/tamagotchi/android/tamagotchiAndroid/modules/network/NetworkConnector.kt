package com.tamagotchi.android.tamagotchiAndroid.modules.network

import com.google.gson.GsonBuilder
import com.tamagotchi.android.tamagotchiAndroid.modules.network.api.UserApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object NetworkConnector{

  val BASE_URL = "https://httpbin.org"

  var okHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    })
    .addInterceptor { chain ->
      chain.request().let {
        it.newBuilder()
          .addHeader("Authentication", "token vale")
          .build()
        chain.proceed(it)
      }
    }
    .build()



  fun <T> createRetrofit(api: Class<T>): T {
    return Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .build()
      .create(api)
  }

}

