package com.dwicandra.storyyukk.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dwicandra.storyyukk.data.local.entity.StoriesEntity

@Dao
interface StoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(story: List<StoriesEntity>)

    @Query("SELECT * FROM stories")
    fun getAllStories(): PagingSource<Int, StoriesEntity>

    @Query("DELETE FROM stories")
    suspend fun deleteAll()
}