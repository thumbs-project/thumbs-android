package com.thumbs.android.thumbsAndroid.core.modules

import com.thumbs.android.thumbsAndroid.BuildConfig
import com.thumbs.android.thumbsAndroid.api.ThumbsApi
import com.thumbs.android.thumbsAndroid.api.UserApi
import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepository
import com.thumbs.android.thumbsAndroid.repositories.ThumbsRepositoryImpl
import com.thumbs.android.thumbsAndroid.repositories.UserRepository
import com.thumbs.android.thumbsAndroid.repositories.UserRepositoryImpl
import com.thumbs.android.thumbsAndroid.ui.register.RegisterContract
import com.thumbs.android.thumbsAndroid.ui.register.RegisterPresenter
import com.thumbs.android.thumbsAndroid.ui.setting.SettingContract
import com.thumbs.android.thumbsAndroid.ui.setting.SettingPresenter
import com.thumbs.android.thumbsAndroid.ui.status.StatusContract
import com.thumbs.android.thumbsAndroid.ui.status.StatusPresenter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(UserApi::class.java)
    }

    single {
        Retrofit.Builder()
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(ThumbsApi::class.java)
    }
}

//test 모듈
val userModule = module {
    factory { UserRepositoryImpl(get()) as UserRepository }
    factory { SettingPresenter(get()) as SettingContract.SettingUserActionListener }
//    factory { SplashPresenter(get()) as SplashContract.SplashUserActionListerner }
}

val registerModule = module {
    factory { ThumbsRepositoryImpl(get()) as ThumbsRepository }
    factory { RegisterPresenter(get()) as RegisterContract.RegisterUserActionListener }
}

val statusModule = module {
    factory { StatusPresenter(get()) as StatusContract.StatusUserActionListener }
}
val appModules = listOf(
    networkModule,
    userModule,
    registerModule,
    statusModule
)
