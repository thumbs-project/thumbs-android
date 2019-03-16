package com.thumbs.android.thumbsAndroid.data.cache

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.thumbs.android.thumbsAndroid.model.ThumbSize


@Entity(tableName = "thumb_size")
data class CacheThumbSize(
    @PrimaryKey var uid: Int = 0,
    @ColumnInfo(name = "width") var width: Int,
    @ColumnInfo(name = "height") var height: Int
)


fun ThumbSize.toCached() = CacheThumbSize(0,this@toCached.width ,this@toCached.height)

fun CacheThumbSize.unCached() =  ThumbSize(this@unCached.width ,this@unCached.height)