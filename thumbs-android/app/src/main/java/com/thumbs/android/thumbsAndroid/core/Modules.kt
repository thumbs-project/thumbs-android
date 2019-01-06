package com.thumbs.android.thumbsAndroid.core

import com.thumbs.android.thumbsAndroid.core.network.api.UserApi
import com.thumbs.android.thumbsAndroid.presenter.setting.SettingContract
import com.thumbs.android.thumbsAndroid.presenter.setting.SettingPresenter
import com.thumbs.android.thumbsAndroid.core.repositories.UserRepository
import com.thumbs.android.thumbsAndroid.core.repositories.UserRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
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


    single {
        Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(UserApi::class.java)
    }
}

val userModule = module {
    factory { UserRepositoryImpl(get()) as UserRepository }
    factory { SettingPresenter(get()) as SettingContract.SettingUserActionListener }
}

val thumbsAppModule = listOf(networkModule, userModule)
