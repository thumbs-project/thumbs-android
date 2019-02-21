package com.thumbs.android.thumbsAndroid.core.modules

import android.arch.persistence.room.Room
import com.thumbs.android.thumbsAndroid.BuildConfig
import com.thumbs.android.thumbsAndroid.api.ThumbsApi
import com.thumbs.android.thumbsAndroid.api.UserApi
import com.thumbs.android.thumbsAndroid.api.UserEventApi
import com.thumbs.android.thumbsAndroid.data.database.AppDatabase
import com.thumbs.android.thumbsAndroid.repositories.*
import com.thumbs.android.thumbsAndroid.ui.splash.SplashContract
import com.thumbs.android.thumbsAndroid.ui.splash.SplashPresenter
import com.thumbs.android.thumbsAndroid.ui.menu.MenuContract
import com.thumbs.android.thumbsAndroid.ui.menu.MenuPresenter
import com.thumbs.android.thumbsAndroid.ui.register.RegisterContract
import com.thumbs.android.thumbsAndroid.ui.register.RegisterPresenter
import com.thumbs.android.thumbsAndroid.ui.setting.SettingContract
import com.thumbs.android.thumbsAndroid.ui.setting.SettingPresenter
import com.thumbs.android.thumbsAndroid.ui.status.StatusContract
import com.thumbs.android.thumbsAndroid.ui.status.StatusPresenter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
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

    single {
        Retrofit.Builder()
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(UserEventApi::class.java)
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "database-thumb"
        ).fallbackToDestructiveMigration().build()
    }

    factory {
        get<AppDatabase>().thumbDao()
    }
}

val repositoryModules = module {
    factory { UserRepositoryImpl(get()) as UserRepository }
    factory { ThumbsRepositoryImpl(get(), get()) as ThumbsRepository }
}
val userModule = module {
    factory { SettingPresenter(get()) as SettingContract.SettingUserActionListener }
    factory { SplashPresenter(get()) as SplashContract.SplashUserActionListerner }
}

val registerModule = module {
    factory { RegisterPresenter(get()) as RegisterContract.RegisterUserActionListener }
}

val statusModule = module {
    factory { StatusPresenter(get()) as StatusContract.StatusUserActionListener }
}

val userEventModule = module {
    single { UserEventRepositoryImpl(get()) as UserEventRepository }
    factory { MenuPresenter(get()) as MenuContract.UserActionListerner }
}

val appModules = listOf(
    networkModule,
    databaseModule,
    repositoryModules,
    userModule,
    registerModule,
    statusModule,
    userEventModule
)

