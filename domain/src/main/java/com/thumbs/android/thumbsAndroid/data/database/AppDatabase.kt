package com.thumbs.android.thumbsAndroid.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.thumbs.android.thumbsAndroid.data.cache.CacheThumbSize
import com.thumbs.android.thumbsAndroid.data.dao.ThumbDao

@Database(entities = [CacheThumbSize::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun thumbDao(): ThumbDao
}