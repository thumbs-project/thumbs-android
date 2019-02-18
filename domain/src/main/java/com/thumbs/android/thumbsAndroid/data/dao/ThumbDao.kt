package com.thumbs.android.thumbsAndroid.data.dao

import android.arch.persistence.room.*
import com.thumbs.android.thumbsAndroid.data.cache.CacheThumbSize
import android.database.sqlite.SQLiteConstraintException
import com.thumbs.android.thumbsAndroid.model.ThumbSize
import okhttp3.Cache


@Dao
abstract class ThumbDao {
    @Query("SELECT * FROM thumb_size LIMIT 1")
    abstract fun selectThumbSize(): CacheThumbSize

    @Insert(onConflict = OnConflictStrategy.FAIL)
    abstract fun insert(entity: CacheThumbSize)

    @Update(onConflict = OnConflictStrategy.FAIL)
    abstract fun update(entity: CacheThumbSize)

    fun upsert(entity: CacheThumbSize) {
        try {
            insert(entity)
        } catch (exception: SQLiteConstraintException) {
            update(entity)
        }
    }
}