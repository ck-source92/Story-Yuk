package com.dwicandra.storyyukk.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "stories")
@Parcelize
data class StoriesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

) : Parcelable